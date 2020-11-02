package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.repo.AccountRepo;
import com.daeng.nyang.service.user.AccountService;
import com.daeng.nyang.service.user.JwtUserDetailService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class AccountController {
	
	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepo userRepo;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager am;
	@Autowired
	private JwtUserDetailService userDetailService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@PostMapping(path="/admin")
	@ApiOperation("관리자계정")
	public void admin(HttpServletRequest req){
		System.out.println("CONTROLLER START");
		System.out.println(req.getHeader("Authorization"));
	}
	


	@PostMapping(path = "/newuser/signup")
	@ApiOperation("회원가입")
	public ResponseEntity<HashMap<String, Object>> signup(@RequestBody Account account) {
		System.out.println("CONTROLLER START");
		
		HashMap<String, Object> result = new HashMap<>();
		result = accountService.signup(account);
		System.out.println(result.toString());
		System.out.println("CONTROLLER END");
		if((boolean)result.get("success"))
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/newuser/signup")
	@ApiOperation("이메일유효성검사")
	public void checkEmail(@RequestParam String email) {
		System.out.println("email유효성 검사 : " + email);
	}

	@PostMapping(path = "/newuser/login")
	@ApiOperation("로그인")
	public ResponseEntity<HashMap<String, Object>> login(@RequestBody Map<String, String> m) {
		System.out.println("CONTROLLER START");
		String user_id = m.get("user_id");
		String user_password = m.get("user_password");
		System.out.println(user_id+" "+user_password);
		HashMap<String, Object> result = accountService.login(user_id, user_password);
		if((boolean)result.get("success"))
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.UNAUTHORIZED);
	}
	
	
	@PostMapping(path = "/user/logout")
	@ApiOperation("로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		
		System.out.println("/user/logout 입장");
		String user_id = null;
//		String accessToken = m.get("accessToken");
		String accessToken= request.getHeader("Authorization");
		System.out.println("reqeust : "+request.getHeader("Authorization"));
		System.out.println(accessToken);
		try {
			System.out.println("controller jwtTokenUtil.getUsernameFromToken");
			user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
			System.out.println(user_id);
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
		} catch (ExpiredJwtException e) { // expire됐을 때
			System.out.println("ExpiredJwtException");
			user_id = e.getClaims().getSubject();
		}

		try {
			ValueOperations<String, Object> vo = redisTemplate.opsForValue();
			System.out.println(user_id);
			if (vo.get(user_id) != null) {
				System.out.println(vo.get(user_id).toString());
				redisTemplate.expire(user_id,1*1000, TimeUnit.MILLISECONDS);
				if(vo.get(accessToken)!=null) {
					System.out.println(vo.get(accessToken).toString());
					redisTemplate.expire(accessToken,1*1000, TimeUnit.MILLISECONDS);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("user does not exist");
		}

		// cache logout token for 10 minutes!
//        logger.info(" logout ing : " + accessToken);
//		redisTemplate.opsForValue().set(accessToken, true);
//		redisTemplate.expire(accessToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(path="/newuser/refresh")
	   @ApiOperation("access토큰이 만료되어서 갱신하고자, refresh토큰을 보냄.")
	    public Map<String, Object>  requestForNewAccessToken(HttpServletRequest request) {
		System.out.println("CONTROLLER START");
	        String accessToken = null;
	        String refreshToken = null;
	        String refreshTokenFromDb = null;
	        String user_id = null;
	        Map<String, Object> response = new HashMap<>();
	        try {
	            accessToken = request.getHeader("Authorization");
	            refreshToken = request.getHeader("refreshToken");
	            System.out.println("accessToken : "+ accessToken);
	            System.out.println("refreshToken : "+ refreshToken);
	            
	            try {
	                user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
	                System.out.println(user_id);
	            } catch (IllegalArgumentException e) {
	            	System.out.println("IllegalArgumentException");
	            } catch (ExpiredJwtException e) { //expire됐을 때
	                user_id = e.getClaims().getSubject();
	            }

	            if (refreshToken != null) { //refresh를 같이 보냈으면.
	                try {
	                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
	                    TotToken result = (TotToken) vop.get(user_id);	// 얘는 refreshToken
	                    refreshTokenFromDb = result.getRefreshToken();
	                    System.out.println("refreshTokenFromDB : "+refreshTokenFromDb);
	                } catch (IllegalArgumentException e) {
	                }
	                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
	                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
	                    final UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
	                    String new_accessToken =  jwtTokenUtil.generateAccessToken(userDetails);
	                    response.put("success", true);
	                    response.put("accessToken", new_accessToken);
	                } else {
	                   response.put("success", false);
	                   response.put("msg", "refresh token is expired.");
	                }
	            } else { //refresh token이 없으면
	               response.put("success", false);
	               response.put("msg", "your refresh token does not exist.");
	            }
	        } catch (Exception e) {
	            throw e;
	        }
	        return response;
	    }
	
	@GetMapping(path="/user/adopt/create")
	public ResponseEntity<HashMap<String, Object>> checkPhone(@RequestParam String phone){
		int rand = (int) (Math.random() * 899999) + 100000;	// 랜덤넘버 6자리
		
		ResponseEntity<HashMap<String, Object>> result = accountService.checkPhone(phone, rand);
		System.out.println(result.toString());
		return result;
	}
	
	@PostMapping(path="/user/adopt/create")
	public ResponseEntity<HashMap<String, Object>> createAdopt(HttpServletRequest request, 
			@RequestBody Apply apply){
		System.out.println("/user/adopt/create 입장");
		String accessToken = request.getHeader("Authorization");
		if(accessToken==null) {
			return null;
		} else if(jwtTokenUtil.isTokenExpired(accessToken)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		// 토큰으로 유저 아이디 받아오는 구문
		String user_id = null; // 일단 null 값
		user_id = jwtTokenUtil.getUsernameFromToken(accessToken); // 토큰을 통해 아이디를 가져오면 null이 아닐 것이다.
		System.out.println("user_id : " + user_id); // 확인
		System.out.println(apply.toString());
		ResponseEntity<HashMap<String, Object>> result = accountService.createApply(user_id, apply);
		return result;
	}

}
