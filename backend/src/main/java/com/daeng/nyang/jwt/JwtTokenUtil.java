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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_ACCESS_TOKEN_VALIDITY = 10 * 60; // 10분
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 12 * 60 * 60; // 12시간
	
	@Value("${jwt.secret}")
	private String secret;
	
	//retrieve username from jwt token
    public String getUsernameFromToken(String token) {
    	System.out.println("JwtTokenUtil : getUsernameFromToken");
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
    	System.out.println("JwtTokenUtil : getExpirationDateFromToken");
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	System.out.println("JwtTokenUtil : getClaimFromToken");
        final Claims claims = getAllClaimsFromToken(token);
        System.out.println(claims.toString());
        return claimsResolver.apply(claims);
    }
    
    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
    	System.out.println("JwtTokenUtil : getAllClaimsFromToken");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public Map<String, Object> getUserParseInfo(String token) {
    	System.out.println("JwtTokenUtil : getUserParseInfo");
        Claims parseInfo = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        System.out.println(parseInfo.toString());
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", parseInfo.getSubject());
        result.put("role", parseInfo.get("role", List.class));
        result.put("user_password", parseInfo.get("user_password"));
        return result;
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
    	System.out.println("JwtTokenUtil : isTokenExpired");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //generate token for user
    public String generateAccessToken(UserDetails userDetails) {
    	System.out.println("JwtTokenUtil : generateAccessToken");
        Map<String, Object> claims = new HashMap<>();
        List<String> li = new ArrayList<>();
        for (GrantedAuthority a: userDetails.getAuthorities()) {
            li.add(a.getAuthority());
        }
        claims.put("role",li);
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public String generateRefreshToken() {
    	System.out.println("JwtTokenUtil : generateRefreshToken");
        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    //while creating the token -
//1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
//2. Sign the JWT using the HS512 algorithm and secret key.
//3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
//   compaction of the JWT to a URL-safe string
    /*
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

     */
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
    	System.out.println("JwtTokenUtil : validateToken");
        final String user_id = getUsernameFromToken(token);
        return (user_id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
