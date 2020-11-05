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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.service.email.EmailService;
import com.daeng.nyang.service.signup.SignupService;
import com.daeng.nyang.service.user.AccountService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

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
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostMapping(path = "/newuser/signup")
	@ApiOperation("회원가입")
	public ResponseEntity<HashMap<String, Object>> signup(@RequestBody Account account) {
		System.out.println("CONTROLLER START");
		System.out.println(account.toString());
		HashMap<String, Object> result;
		result = accountService.signup(account);
		System.out.println(result.toString());
		System.out.println("CONTROLLER END");
		if ((boolean) result.get("success"))
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/newuser/signup")
	@ApiOperation("이메일 유효성 검사")
	public ResponseEntity<?> checkEmail(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isAvailabe = signupService.checkEmail(email); // 사용가능한 email
		if (isAvailabe) { // 사용가능하면
			String auth_number = emailService.sendAuthEmail(email);// 인증번호
			System.out.println(auth_number);
//         String hash_number = BCrypt.hashpw(auth_number, SALT_VALUE); // hash처리
			String hash_number = BCrypt.hashpw(auth_number, salt);
//         BCrypt.checkpw(plaintext, hashed);
			resultMap.put("origin_hash", hash_number);
			return ResponseEntity.status(HttpStatus.OK).body(resultMap);
		} else {
			resultMap.put("origin_hash", null);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
		}
	}

	@GetMapping(path = "/newuser/signup/hashcheck")
	@ApiOperation("인증번호 유효성검사")
	public ResponseEntity<?> checkAuthNumber(@RequestParam String auth_number, @RequestParam String hash_number) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean result = BCrypt.checkpw(auth_number, hash_number);
		if (result) {
			resultMap.put("result", result);
			return ResponseEntity.status(HttpStatus.OK).body(resultMap);
		} else {
			resultMap.put("result", result);
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
	}

	@PostMapping(path = "/newuser/login")
	@ApiOperation("로그인")
	public ResponseEntity<HashMap<String, Object>> login(@RequestBody Map<String, String> m) {
		System.out.println("CONTROLLER START");
		String user_id = m.get("user_id");
		String user_password = m.get("user_password");
		System.out.println(user_id + " " + user_password);
		HashMap<String, Object> result = accountService.login(user_id, user_password);
		if ((boolean) result.get("success"))
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.OK);
		else
			return new ResponseEntity<HashMap<String, Object>>(result, HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "/user/logout")
	@ApiOperation("로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request) {

		System.out.println("CONTROLLER START");
		String user_id = null;
//      String accessToken = m.get("accessToken");
		String accessToken = request.getHeader("Authorization");
		System.out.println("reqeust : " + request.getHeader("Authorization"));
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
				redisTemplate.expire(user_id, 1 * 1000, TimeUnit.MILLISECONDS);
				if (vo.get(accessToken) != null) {
					System.out.println(vo.get(accessToken).toString());
					redisTemplate.expire(accessToken, 1 * 1000, TimeUnit.MILLISECONDS);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.println("user does not exist");
		}
		return new ResponseEntity(HttpStatus.OK);
	}

	@PostMapping(path = "/newuser/refresh")
	@ApiOperation("access토큰이 만료되어서 갱신하고자, refresh토큰을 보냄.")
	public ResponseEntity<HashMap<String, Object>> requestForNewAccessToken(HttpServletRequest request) {
		System.out.println("CONTROLLER START");
		String accessToken = request.getHeader("accessToken");
		String refreshToken = request.getHeader("refreshToken");
		HashMap<String, Object> response;
		response = accountService.refreshToken(accessToken, refreshToken);
		System.out.println("CONTROLLER END");
		if ((boolean) response.get("success"))
			return new ResponseEntity<>(response, HttpStatus.OK);
		else
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(path = "/user/adopt/create")
	@ApiOperation("문자인증")
	public ResponseEntity<HashMap<String, Object>> checkPhone(@RequestParam String phone) {
		int rand = (int) (Math.random() * 899999) + 100000; // 랜덤넘버 6자리

		HashMap<String, Object> result = accountService.checkPhone(phone, rand);
		System.out.println(result.toString());
		if (result == null)
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping(path = "/user/adopt/create") //
	@ApiOperation("입양신청서 저장")
	public ResponseEntity<HashMap<String, Object>> createAdopt(@RequestBody Apply apply, HttpServletRequest request) {
		System.out.println("CONTROLLER START");
		String accessToken = request.getHeader("Authorization");
		String user_id = null;
		user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
		System.out.println("user_id : " + user_id); // 확인
		System.out.println(apply.toString());
		HashMap<String, Object> result = accountService.createApply(user_id, apply);
		if ((boolean) result.get("success"))
			return new ResponseEntity<>(result, HttpStatus.OK);
		else
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(path = "/user/adopt/read")
	public ResponseEntity<HashMap<String, Object>> readAdopt(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
		HashMap<String, Object> map = accountService.readAdopt();
		map.put("user_id", user_id);
		System.out.println(map.toString());
		System.out.println("CONTROLLER END");
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}

	@GetMapping(path = "/user/adopt/read/{uid}")
	public ResponseEntity<HashMap<String, Object>> readone(HttpServletRequest request, @PathVariable long uid) {
		System.out.println("CONTROLLER START");
		String accessToken = request.getHeader("Authorization");
		String user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
		System.out.println("uid : " + uid);
		HashMap<String, Object> result = accountService.readAdopt(uid, user_id);
		System.out.println(result.toString());
		System.out.println("CONTROLLER END");
		if (result == null)
			return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
		else return new ResponseEntity<>(result, HttpStatus.OK);
	}

}