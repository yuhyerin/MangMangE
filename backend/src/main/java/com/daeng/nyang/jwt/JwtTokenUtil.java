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
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 7; // 일주일
	
	@Value("${jwt.secret}")
	private String secret;
	
	/** 토큰으로부터 user_id를 얻음 */
    public String getUsernameFromToken(String token) {
    	System.out.println("JwtTokenUtil : getUsernameFromToken");
        return getClaimFromToken(token, Claims::getSubject);
    }

    /** 토큰의 만료시간을 반환  */ 
    public Date getExpirationDateFromToken(String token) {
    	System.out.println("JwtTokenUtil : getExpirationDateFromToken");
        return getClaimFromToken(token, Claims::getExpiration);
    }
    
    /** 토큰으로부터 정보를 얻음 */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	System.out.println("JwtTokenUtil : getClaimFromToken");
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /** Jwts parser를 사용해서 JWT토큰을 파싱하는 함수 */
    private Claims getAllClaimsFromToken(String token) {
    	System.out.println("JwtTokenUtil - getAllClaimsFromToken()호출 : JWT파싱하는 함수");
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /** 토큰으로부터 정보를 파싱한다. */
    public Map<String, Object> getUserParseInfo(String token) {
    	System.out.println("JwtTokenUtil : getUserParseInfo");
        Claims parseInfo = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Map<String, Object> result = new HashMap<>();
        result.put("user_id", parseInfo.getSubject());
        result.put("role", parseInfo.get("role", List.class));
        return result;
    }

    /** 토큰이 만료되었는지 검사 */
    public Boolean isTokenExpired(String token) {
    	System.out.println("JwtTokenUtil : isTokenExpired");
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    /** AccessToken 생성. */
    public String generateAccessToken(UserDetails userDetails) {
    	System.out.println("JwtTokenUtil : generateAccessToken");
        Map<String, Object> claims = new HashMap<>();
//        List<String> li = new ArrayList<>();
//        for (GrantedAuthority a: userDetails.getAuthorities()) {
//            li.add(a.getAuthority());
//        }
        System.out.println("ROLE: "+userDetails.getAuthorities());
        claims.put("role",userDetails.getAuthorities());
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /** RefreshToken 생성. */
    public String generateRefreshToken(String user_id) {
    	System.out.println("JwtTokenUtil : generateRefreshToken");
        return Jwts.builder().setSubject(user_id).setIssuedAt(new Date(System.currentTimeMillis()))
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
