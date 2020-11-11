package com.daeng.nyang.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.daeng.nyang.service.user.JwtUserDetailService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
   @Autowired
   private JwtTokenUtil jwtTokenUtil;

   @Autowired
   RedisTemplate<String, Object> redisTemplate;

//   @Autowired
//   private JwtUserDetailService jwtUserDetailService;

   public Authentication getAuthentication(String token) {
      System.out.println("getAuthentication : " + token);
      Map<String, Object> parseInfo = jwtTokenUtil.getUserParseInfo(token);
      List<String> rs = (List) parseInfo.get("role");
      Collection<GrantedAuthority> tmp = new ArrayList<>();
      for (String a : rs) {
         tmp.add(new SimpleGrantedAuthority(a));
      }
      UserDetails userDetails = User.builder().username(String.valueOf(parseInfo.get("user_id"))).authorities(tmp)
            .password(String.valueOf(parseInfo.get("user_password"))).build();
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
      System.out.println(userDetails.toString());
      return usernamePasswordAuthenticationToken;
   }

   @Bean
   public FilterRegistrationBean JwtRequestFilterRegistration(JwtRequestFilter filter) {
      System.out.println("JwtRequestFilterRegistration : " + filter.toString());

      FilterRegistrationBean registration = new FilterRegistrationBean(filter);
      registration.setEnabled(false);
      return registration;
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
         throws ServletException, IOException {
      System.out.println("doFilterInternal");
      System.out.println("REQUEST : " + request.getHeader("Authorization"));
      String requestTokenHeader = request.getHeader("Authorization");
      String userid = null;

      if (requestTokenHeader != null) {
         try {
            userid = jwtTokenUtil.getUsernameFromToken(requestTokenHeader);
         } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException in doFilterInternal");
         }
      }
      if (userid == null) {
         System.out.println("userid null");
      } else if (redisTemplate.opsForValue().get(requestTokenHeader) == null) {
         System.out.println("redis ACCESS TOKEN timeout");
      } else {
         // DB access 대신에 파싱한 정보로 유저 만들기!
         Authentication authen = getAuthentication(requestTokenHeader);
         System.out.println(authen.toString());
         // 만든 authentication 객체로 매번 인증받기
         SecurityContextHolder.getContext().setAuthentication(authen);
         response.setHeader("Access-Control-Allow-Origin", "*");
         response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
         response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, enctype");
         response.setHeader("Access-Control-Max-Age", "3600");
         response.setHeader("username", userid);
         
      }
      chain.doFilter(request, response);
   }

}