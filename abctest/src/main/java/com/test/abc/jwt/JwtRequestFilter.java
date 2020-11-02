package com.test.abc.jwt;

import java.io.IOException;
import java.util.function.Function;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	// K : user_id, V : refreshToken
	// K : accessToken, V : Account

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestTokenHeader = null;
		String userid = null;
		String jwtToken = null;
		try {
			requestTokenHeader = request.getHeader("Authorization");
			if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
				jwtToken = requestTokenHeader.substring(7);
				System.out.println(jwtToken);
				if(redisTemplate.opsForValue().get(jwtToken)!=null) {
					Claims claim = Jwts.parser().setSigningKey("daengnyang").parseClaimsJws(jwtToken).getBody();
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
        } 
		
		
		filterChain.doFilter(request, response);
		
	}

}
