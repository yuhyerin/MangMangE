package com.test.abc.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@Configuration
public class JwtTokenProvider implements Serializable {
	
	private static final long serialVersionUID = -2550185165626007488L;
//	@Value("${jwt.JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY = "600"; 
//	@Value("${jwt.JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY = "3600"; 
	
//	@Value("${jwt.secret}")
	private String secretKey = "daengnyang";
	
	public String createToken(String subject) {
		System.out.println(JWT_ACCESS_TOKEN_VALIDITY);
		System.out.println(JWT_REFRESH_TOKEN_VALIDITY);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().
        		setClaims(claims).
        		setSubject(subject).
        		setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String createRefreshToken() {
        return Jwts.builder().
        		setIssuedAt(new Date(System.currentTimeMillis())).
        		setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_REFRESH_TOKEN_VALIDITY) * 1000)).
        		signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

}
