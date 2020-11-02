package com.daeng.nyang.controller;

import java.util.Collection;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;

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
	private JwtUserDetailsService jwtuserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostMapping(path = "/newuser/signup")
	@ApiOperation("회원가입")
	public ResponseEntity<?> signup(@RequestBody Account user) {
		
		Map<String, Object> ResultMap = new HashMap<>();
		String user_id = user.getUser_id();
		if (userRepo.findByUserid(user_id) == null) {
			if ("admin".equals(user_id)) { // 아이디가 admin일때만 ROLE_ADMIN 권한 부여.
				user.setRole("ROLE_ADMIN");
			} else {
				user.setRole("ROLE_USER");
			}
			user.setUser_password(bcryptEncoder.encode(user.getUser_password())); // 암호화 해서 저장
			userRepo.save(user);
			ResultMap.put("success", true);
		} else {
			ResultMap.put("success", false);
			ResultMap.put("message", "already exist user_id");
		}
		return ResponseEntity.status(HttpStatus.OK).body(ResultMap);
	}

	@GetMapping(path = "/newuser/signup")
	@ApiOperation("이메일 유효성 검사")
	public ResponseEntity<?> checkEmail(@RequestParam String email) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean isAvailabe = signupService.checkEmail(email); // 사용가능한 email
		if (isAvailabe) { // 사용가능하면
			String auth_number = emailService.sendAuthEmail(email);// 인증번호
			String hash_number = BCrypt.hashpw(auth_number, "daeng"); // hash처리
			resultMap.put("origin_hash", hash_number);
		} else {
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
	public ResponseEntity<?> login(@RequestBody Map<String, String> User) {

		System.out.println("로그인 컨트롤러 진입");
		Map<String, Object> Resultmap = new HashMap<>();
		String user_id = User.get("user_id");
		String user_password = User.get("user_password");
		
		// 순서 : 선 인증(id, password 확인) , 후 인가
		/** 여기서 인증 */
		try {
			/**
			 * AuthenticationManager 하는 일 - user_id, user_password 인증
			 * UsernamePasswordAuthenticationToken 은 내부적을 UserDetailsService를 사용한다.
			 */
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user_id, user_password));
		} catch (Exception e) {
			System.out.println("인증 에러 (아이디, 비밀번호가 맞지 않음) 여기 걸려도 아래로 쭉 이동하기 때문에 return 해버려야 함. ");
			/** 인증은 401에러, 인가는 403에러 */
			Resultmap.put("result", "UNAUTHORIZED");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Resultmap);
		}
		
		/** 인증이 성공해서 인가 과정 */
		UserDetails userDetails = jwtuserDetailsService.loadUserByUsername(user_id);
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		System.out.println("이 유저의 롤은? "+roles.toString());

		/** AccessToken 생성 */
		String accessToken = jwtTokenUtil.generateAccessToken(userDetails);

		/** RefreshToken 생성 */
		String refreshToken = jwtTokenUtil.generateRefreshToken(user_id);

		System.out.println("access , refresh 토큰 생성 완료 ");

		/** 발급한 토큰을 redis에 저장 */
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		Token retok = Token.builder().refreshToken(refreshToken).build();
		vop.set(user_id, retok, Long.parseLong(JWT_REFRESH_TOKEN_VALIDITY) * 1000, TimeUnit.MILLISECONDS);
		Account user = Account.builder().user_id(user_id).build();
		retok = Token.builder().account(user).build();
		vop.set(accessToken, retok, Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000, TimeUnit.MILLISECONDS);
		System.out.println("발급한 엑세스토큰, 리프레시토큰을 redis에 저장 완료 ");
		
		/** 프론트에 엑세스토큰, 리프레쉬토큰을 보내줌 */
		Resultmap.put("accessToken", accessToken);
		Resultmap.put("refreshToken", refreshToken);

		return ResponseEntity.status(HttpStatus.OK).body(Resultmap);
	}

	@PostMapping(path = "/newuser/logout")
	@ApiOperation("로그아웃")
	public ResponseEntity<?> logout(HttpServletRequest request) {

		System.out.println("/user/logout 입장");
		String user_id = null;
		String accessToken = request.getHeader("Authorization");
		System.out.println("accessToken : " + accessToken);
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
	}// end logout

	@PostMapping(path = "/newuser/refresh")
	@ApiOperation("access토큰이 만료되어서 갱신하고자, refresh토큰을 보냄.")
	public Map<String, Object> requestForNewAccessToken(@RequestBody Map<String, String> twotokens) {
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

			} catch (ExpiredJwtException e) { // expire됐을 때
				user_id = e.getClaims().getSubject();
			}

			if (refreshToken != null) { // refresh를 같이 보냈으면.
				try {
					ValueOperations<String, Object> vop = redisTemplate.opsForValue();
					Token result = (Token) vop.get(user_id);
					refreshTokenFromDb = result.getRefreshToken();
				} catch (IllegalArgumentException e) {
				}
				// 둘이 일치하고 만료도 안됐으면 재발급 해주기.
				if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
					final UserDetails userDetails = jwtuserDetailsService.loadUserByUsername(user_id);
					String new_accessToken = jwtTokenUtil.generateAccessToken(userDetails);
					response.put("success", true);
					response.put("accessToken", new_accessToken);
				} else {
					response.put("success", false);
					response.put("msg", "refresh token is expired.");
				}
			} else { // refresh token이 없으면
				response.put("success", false);
				response.put("msg", "your refresh token does not exist.");
			}

		} catch (Exception e) {
			throw e;
		}

		return response;
	}

}
