package com.daeng.nyang.jwt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_ACCESS_TOKEN_VALIDITY = 10 * 60; // 10분
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 7; // 일주일
	
	@Value("${jwt.secret}")
	private String secret;
	
	public String getUsernameFromToken(String token) {
		System.out.println("JwtTokenUtil : getUsernameFromToken [ 토큰으로부터 user_id를 얻음 ] ");
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	System.out.println("JwtTokenUtil : getClaimFromToken");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	System.out.println("JwtTokenUtil - getAllClaimsFromToken()호출 : JWT파싱하는 함수");
    	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
    	System.out.println("JwtTokenUtil : isTokenExpired");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
    	System.out.println("JwtTokenUtil : getExpirationDateFromToken");
        return getClaimFromToken(token, Claims::getExpiration);
    }

    
    /** AccessToken 생성. */
    public String generateAccessToken(UserDetails userDetails) {
    	System.out.println("JwtTokenUtil : generateAccessToken");
        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();
        for (GrantedAuthority a: userDetails.getAuthorities()) {
            li.add(a.getAuthority());
        }
        System.out.println("JwtTokenUtil - generateAccessToken()메소드  ROLE: "+userDetails.getAuthorities());
        claims.put("role",userDetails.getAuthorities());
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(userDetails.getUsername())
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /** RefreshToken 생성. */
    public String generateRefreshToken(String user_id) {
    	System.out.println("JwtTokenUtil : generateRefreshToken");
        return Jwts.builder()
        		.setSubject(user_id)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    //validate tokens
    public Boolean validateToken(String token, UserDetails userDetails) {
    	System.out.println("JwtTokenUtil : validateToken");
        final String user_id = getUsernameFromToken(token);
        return (user_id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
