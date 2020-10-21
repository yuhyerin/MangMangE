package com.daeng.nyang.service.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.daeng.nyang.dto.AuthenticationResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {
	
	@Value("${jwt.salt}")
	private String salt;
	
	@Value("${jwt.expmin}")
	private Long expireMin;
	
	// 로그인 성공 시 사용자 정보를 기반으로 JWTToken을 생성해서 반환한다.
	public String create(final AuthenticationResponse user) {

		
		System.out.println("JwtService.create() : 로그인 성공 시 사용자 정보를 기반으로 JwtToken을 만들어서 반환해준다. ");
		System.out.println("time: "+ expireMin);
		
		final JwtBuilder builder = Jwts.builder();
		
		// JWT 토큰 = 헤더 + 페이로드 + 서명 
		
		//1. 헤더
		builder.setHeaderParam("typ", "JWT"); // 토큰타입
		//2. 페이로드
		builder.setSubject("로그인토큰")
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*expireMin))
				.claim("AuthenticationResponse",user); //데이터 더 담고싶으면 .claim("second", "더담을 데이터");
		//3. 서명 - 비밀키를 이용한 암호화.
		builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
		// 마지막 직렬화 처리 
		final String jwt = builder.compact();
		System.out.println("토큰 발행!!! : "+jwt);
		return jwt;
	}
	
	// 전달받은 토큰이 유효한지 확인하고 , 문제가 있다면 Runtime 예외를 발생시킨다.
	public boolean checkValid(final String jwt) {
				
		// 토큰의 유효성 확인 
		try {
			System.out.println("전달받은 토큰-> "+jwt);
			Jws<Claims> jwtclaims = getDecodeToken(jwt);
			JwtBuilder builder = Jwts.builder();
			builder.setHeaderParam("typ", "JWT");
			builder.setClaims(jwtclaims.getBody());
			builder.signWith(SignatureAlgorithm.HS256, salt.getBytes());
			String newtoken = builder.compact();
			System.out.println("비교를 위해 새로만든 토큰-> "+newtoken);
			
			if(jwt.equals(newtoken)) {
				System.out.println("이 토큰은 유효합니다!");
				return true;
			}else {
				System.out.println("이 토큰은 변조가 의심됩니다!");
				return false;
			}
			
//			Claims claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt).getBody();
			
		}catch(ExpiredJwtException e) {
			System.out.println(" 토큰이 만료되었습니다! ");
			return false;
		}catch(JwtException e) {
			System.out.println("토큰이 변조되었습니다! ");
			return false;
		}
		
	}
	
	// jwt 토큰을 해독해서 그 회원이 맞는지 정보를 반환한다.
	public Jws<Claims> getDecodeToken(final String jwt){
		Jws<Claims> claims = null;
		
		try {
			System.out.println("토큰 해독중 . . .");
			claims = Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
			return claims;
			
		}catch(final Exception e) {
			
			throw new RuntimeException();
		}
		
	}
	
	// 토큰 만료 확인
	public Boolean getExpToken(String jwt) {
		return true;
	}
	
}
