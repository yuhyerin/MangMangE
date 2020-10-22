package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Token;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.repo.AccountRepo;
import com.daeng.nyang.service.user.JwtUserDetailService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class AccountController {

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
	
	@GetMapping("/")
	public String home() {
		return "home";
	}

	@PostMapping(path = "/user/signup")
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
			map.put("success", true);
			System.out.println(user.toString());
			userRepo.save(user);
		} else {
			map.put("success", false);
			map.put("message", "duplicated user_id");
		}
		return map;
	}
	
	@GetMapping(path="/user/signup")
	@ApiOperation("이메일유효성검사")
	public void checkEmail(@RequestParam String email) {
		System.out.println("email유효성 검사 : "+email);
	}
	
	@PostMapping(path="/user/login")
	@ApiOperation("로그인")
	public Map<String, Object> login(@RequestBody Map<String, String> m) {
		String user_id = m.get("user_id");
		String user_password = m.get("user_password");
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(user_id, user_password));
		} catch(Exception e) {
			System.out.println("authenticate error");
			e.printStackTrace();
		}
		
		UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
		String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
		String refreshToken = jwtTokenUtil.generateRefreshToken(user_id);
		
		Token retok = Token.builder().user_id(user_id).refreshToken(refreshToken).build();
//        retok.setUsername(username);
//        retok.setRefreshToken(refreshToken);
		
		 //generate Token and save in redis
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(user_id, retok);
        
//        logger.info("generated access token: " + accessToken);
//        logger.info("generated refresh token: " + refreshToken);
        System.out.println("generated access token : "+accessToken);
        System.out.println("generated refresh token : "+refreshToken);
        
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
	}

	
}
