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

import com.daeng.nyang.service.user.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private JwtUserDetailsService jwtUserDetailService;

	/** 프로젝트 실행시 제일 먼저 진입 */
	@Bean
	public FilterRegistrationBean JwtRequestFilterRegistration(JwtRequestFilter filter) {
		System.out.println("첫번째 진입 : JwtRequestFilterRegistration /전달된 파라미터 명 :" + filter.toString());
		FilterRegistrationBean registration = new FilterRegistrationBean(filter);
		registration.setEnabled(false);
		return registration;
	}
	
	/** 인증 */
	public Authentication getAuthentication(String token) {
		System.out.println("getAuthentication : " + token);
		/** 토큰으로부터 유저 정보를 파싱한다. (user_id, role) */
		Map<String, Object> parseInfo = jwtTokenUtil.getUserParseInfo(token);
		List<String> roles = (List) parseInfo.get("role");
		Collection<GrantedAuthority> tmp = new ArrayList<>();
		for (String a : roles) {
			tmp.add(new SimpleGrantedAuthority(a));
		}
		UserDetails userDetails = User.builder().username(String.valueOf(parseInfo.get("user_id"))).authorities(tmp)
				.password(String.valueOf(parseInfo.get("password"))).build();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		System.out.println(userDetails.toString());
		return usernamePasswordAuthenticationToken;
	}

	
	/** 요청하면 여기로 제일먼저 들어옴 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		System.out.println("요청하면 여기로 제일먼저 들어옴 - JwtRequestFilter의 doFilterInternal()메소드 ");
		String jwtToken = request.getHeader("Authorization");
		String user_id = request.getParameter("user_id");
		String user_password = request.getParameter("user_password");
		
		if (jwtToken != null) {// 토큰이 있으면
			System.out.println("토큰이 있음");
			try {
				// 일단 토큰에서 user_id 얻음.
				user_id = jwtTokenUtil.getUsernameFromToken(jwtToken);
				System.out.println("토큰에서 꺼낸 user_id : "+user_id);
				
				// id정보도 있고, 토큰이 만료되지 않았으면
				if(user_id != null && !jwtTokenUtil.isTokenExpired(jwtToken)) {
					System.out.println("id정보도 있고 토큰도 만료되지 않음.");
					// DB에 접근하지 않고, 파싱한 정보로 유저 만들기.
					Authentication authentication = getAuthentication(jwtToken);
					// 만든 authentication 객체로 인증받기.
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}else if(jwtTokenUtil.isTokenExpired(jwtToken)) {
					System.out.println("토큰이 만료되었습니다.");
					
				}
				
			} catch (IllegalArgumentException e) {
				System.out.println("trytry catchcatch");
			}
		}else {
			System.out.println("토큰 자체가 존재하지 않습니다.");
			UserDetails userDetails = jwtUserDetailService.loadUserByUsername(user_id);
			jwtTokenUtil.generateAccessToken(userDetails);
		}
		
		
		
		
//		if (user_id == null) {
//			System.out.println("userid null");
//			logger.info("token maybe expired: username is null.");
//		}
////		} else if (redisTemplate.opsForValue().get(jwtToken) != null) {
//			else if(redisTemplate.opsForValue().get(requestTokenHeader) != null) {
////			System.out.println("redis null");
//			logger.warn("this token already logout!");
//		} else {
//			// DB access 대신에 파싱한 정보로 유저 만들기!
//			System.out.println("else authen");
////			Authentication authen = getAuthentication(jwtToken);
//			Authentication authen = getAuthentication(requestTokenHeader);
//			System.out.println(authen.toString());
//			// 만든 authentication 객체로 매번 인증받기
//			SecurityContextHolder.getContext().setAuthentication(authen);
//			response.setHeader("username", user_id);
//		}
		
		chain.doFilter(request, response);
	}
}