package com.daeng.nyang.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.BasicResponse;
import com.daeng.nyang.dto.Token;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.repo.AccountRepo;
import com.daeng.nyang.service.email.EmailService;
import com.daeng.nyang.service.signup.SignupService;
import com.daeng.nyang.service.user.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class AccountController {

	@Autowired
	private SignupService signupService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private AccountRepo userRepo;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostMapping(path = "/newuser/signup")
	@ApiOperation("회원가입")
	public Map<String, Object> signup(@RequestBody Account user) {
		String user_id = user.getUser_id();
		Map<String, Object> map = new HashMap<>();
		if (userRepo.findByUserid(user_id) == null) {
			if ("admin".equals(user_id)) {
				user.setRole("ROLE_ADMIN");
			} else {
				user.setRole("ROLE_USER");
			}
			user.setUser_password(bcryptEncoder.encode(user.getUser_password()));
			userRepo.save(user);
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("message", "already exist user_id");
		}
		return map;
	}

	@GetMapping(path = "/newuser/signup")
	@ApiOperation("이메일 유효성 검사")
	public ResponseEntity<?> checkEmail(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isAvailabe = signupService.checkEmail(email); // 사용가능한 email
		if(isAvailabe) { //사용가능하면 
			String auth_number = emailService.sendAuthEmail(email);// 인증번호 
			String hash_number = BCrypt.hashpw(auth_number, "daeng"); // hash처리
			resultMap.put("origin_hash", hash_number);
		}else {
			resultMap.put("origin_hash", null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}
	
	@GetMapping(path = "/newuser/signup/hashcheck")
	@ApiOperation("인증번호 유효성검사")
	public ResponseEntity<?> checkAuthNumber(@RequestParam String auth_number) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String hash_number = BCrypt.hashpw(auth_number, "daeng");
		resultMap.put("my_hash", hash_number);
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}

	@PostMapping(path = "/newuser/login")
	@ApiOperation("로그인")
	public Map<String, Object> login(@RequestBody Map<String, String> User) {
		System.out.println("AccountController - /newuser/login");
		String user_id = User.get("user_id");
		String user_password = User.get("user_password");
		try {
			/** AuthenticationManager 하는 일 - user_id, user_password 인증
			 * UsernamePasswordAuthenticationToken 은 내부적을 UserDetailsService를 사용한다. */
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user_id, user_password));
		} catch (Exception e) {
			System.out.println("인증 에러 (아이디, 비밀번호가 맞지 않음)");
			e.printStackTrace();
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(user_id);
		Collection<? extends GrantedAuthority> c = userDetails.getAuthorities();
		String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
		String refreshToken = jwtTokenUtil.generateRefreshToken(user_id);

		Token retok = new Token();
        retok.setUser_id(user_id);
        retok.setRefreshToken(refreshToken);
		
        // generate Token and save in redis
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		vop.set(user_id, retok);
		
		Map<String, Object> map = new HashMap<>();
		map.put("accessToken", accessToken);
		map.put("refreshToken", refreshToken);
		return map;
	}

	@PostMapping(path = "/user/logout")
	@ApiOperation("로그아웃")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> m) {
		System.out.println("/user/logout 입장");
		String user_id = null;
		String accessToken = m.get("accessToken");
		System.out.println("accessToken : "+accessToken);
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
				boolean flag = redisTemplate.delete(user_id);
			}
		} catch (IllegalArgumentException e) {
			System.out.println("user does not exist");
		}
		
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@PostMapping(path="/newuser/refresh")
	@ApiOperation("access토큰이 만료되어서 갱신하고자, refresh토큰을 보냄.")
    public Map<String, Object>  requestForNewAccessToken(@RequestBody Map<String, String> twotokens) {
        String accessToken = null;
        String refreshToken = null;
        String refreshTokenFromDb = null;
        String user_id = null;
        Map<String, Object> response = new HashMap<>();
        try {
            accessToken = twotokens.get("accessToken");
            refreshToken = twotokens.get("refreshToken");
            try {
                user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) { //expire됐을 때
                user_id = e.getClaims().getSubject();
            }

            if (refreshToken != null) { //refresh를 같이 보냈으면.
                try {
                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                    Token result = (Token) vop.get(user_id);
                    refreshTokenFromDb = result.getRefreshToken();
                } catch (IllegalArgumentException e) {
                }
                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(user_id);
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

}
