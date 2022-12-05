package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.daeng.nyang.common.DaengNyangResponse;
import com.daeng.nyang.controller.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.entity.Account;
import com.daeng.nyang.dto.AnimalListFE;
import com.daeng.nyang.entity.Apply;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.service.animal.AnimalService;
import com.daeng.nyang.service.email.EmailService;
import com.daeng.nyang.service.signup.SignupService;
import com.daeng.nyang.service.user.AccountService;

import io.swagger.annotations.ApiOperation;

@Slf4j
@RestController
@CrossOrigin("*")
public class AccountController {

	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;

	@Value("${jwt.secret}")
	private String SALT_VALUE;

	private static String salt = BCrypt.gensalt();

	@Autowired
	private AccountService accountService;

	@Autowired
	private SignupService signupService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AnimalService animalService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;



	@PostMapping(path = "/newuser/login")
	@ApiOperation("로그인")
	public ResponseEntity<HashMap<String, Object>> login(@RequestBody Map<String, String> m) {
		String user_id = m.get("user_id");
		String user_password = m.get("user_password");
		HashMap<String, Object> result = accountService.login(user_id, user_password);
		if ((boolean) result.get("success"))
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "/newuser/refresh")
	@ApiOperation("accessTOKEN 갱신")
	public ResponseEntity<HashMap<String, Object>> requestForNewAccessToken(HttpServletRequest request) {
		String accessToken = request.getHeader("accessToken");
		String refreshToken = request.getHeader("refreshToken");
		HashMap<String, Object> response;
		response = accountService.refreshToken(accessToken, refreshToken);
		if ((boolean) response.get("success"))
			return new ResponseEntity<>(response, HttpStatus.OK);
		else
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	// 회원
//	@PostMapping(path = "/user/changepw")
//	@ApiOperation("비밀번호 변경")
//	public ResponseEntity<HashMap<String, Object>> findUserId(@RequestBody Account account) {
//		HashMap<String, Object> map = accountService.changPW(account);
//		return new ResponseEntity<>(map, HttpStatus.OK);
//	}

	@PostMapping(path = "/user/logout")
	@ApiOperation("로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		String user_id = null;
		try {
			TotToken user = (TotToken) redisTemplate.opsForValue().get(accessToken);
			user_id = user.getAccount().getUserId();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			ValueOperations<String, Object> vo = redisTemplate.opsForValue();
			if (vo.get(user_id) != null) {
				redisTemplate.expire(user_id, 1, TimeUnit.SECONDS);
				if (vo.get(accessToken) != null) {
					redisTemplate.expire(accessToken, 1, TimeUnit.SECONDS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(path = "/user/adopt/create")
	@ApiOperation("문자인증")
	public ResponseEntity<HashMap<String, Object>> checkPhone(@RequestParam String phone) {
		int rand = (int) (Math.random() * 899999) + 100000; // 랜덤넘버 6자리
		HashMap<String, Object> result = accountService.checkPhone(phone, rand);
		if (result == null)
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(path = "/user/adopt/create")
	@ApiOperation("입양신청서 저장")
	public ResponseEntity<HashMap<String, Object>> createAdopt(@RequestBody Apply apply, HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		try {
			TotToken user = (TotToken) redisTemplate.opsForValue().get(accessToken);
			String user_id = user.getAccount().getUserId();
			HashMap<String, Object> result = accountService.createApply(user_id, apply);
			if ((boolean) result.get("success"))
				return new ResponseEntity<>(result, HttpStatus.OK);
			else
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping(path = "/user/adopt/read")
	public ResponseEntity<HashMap<String, Object>> readAdopt(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
		HashMap<String, Object> map = accountService.readAdopt();
		map.put("user_id", user_id);
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}

	@GetMapping(path = "/user/adopt/read/{uid}")
	public ResponseEntity<HashMap<String, Object>> readone(@PathVariable long uid, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Apply apply = accountService.readAdopt(uid);
		AnimalListFE animal = animalService.animalDetail(apply.getAni_num());
		
		if(apply==null || animal==null)
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		else {
			result.put("apply", apply);
			result.put("animal", animal);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/** 관리자인지 아닌지 판별. 이거 ROLE을 비교하는걸로 수정해야 할 것 같습니다! */
	@GetMapping(path = "/user/userId")
	public ResponseEntity<HashMap<String, Object>> userID(HttpServletRequest request) {
		TotToken user = (TotToken) redisTemplate.opsForValue().get(request.getHeader("Authorization"));
		HashMap<String, Object> map = new HashMap<>();
		if (user.getAccount().getUserId().contains("admin")) {
			map.put("success", true);
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		map.put("success", false);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
