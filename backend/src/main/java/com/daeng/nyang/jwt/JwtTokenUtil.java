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
	
	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;
	
	@Value("${jwt.secret}")
	private String secret;
	

	//retrieve username from jwt token
    public String getUsernameFromToken(String token) {
    	String result = getClaimFromToken(token, Claims::getSubject);
        return result;

    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Map<String, Object> getUserParseInfo(String token) {
        Claims parseInfo = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", parseInfo.getSubject());
        result.put("role", parseInfo.get("role", List.class));
        result.put("user_password", parseInfo.get("user_password"));
        return result;
    }
    
    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    
    /** AccessToken 생성. */
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();
        for (GrantedAuthority a: userDetails.getAuthorities()) {
        	
            li.add(a.getAuthority());
        }

        claims.put("role",li);
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(JWT_REFRESH_TOKEN_VALIDITY) * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


    //validate tokens
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String user_id = getUsernameFromToken(token);
        return (user_id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
