package com.daeng.nyang.service.user;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.repo.AccountRepo;
import com.daeng.nyang.repo.AnimalRepo;
import com.daeng.nyang.repo.ApplyRepo;

import io.jsonwebtoken.ExpiredJwtException;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class AccountService {

	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private ApplyRepo applyRepo;
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUserDetailService jwtUserDetailService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private JwtUserDetailService userDetailService;

	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;

	@Value("${apiKey}")
	private String apiKey;
	@Value("${apiSecret}")
	private String apiSecret;

	// 회원가입
	public HashMap<String, Object> signup(Account account) {
		HashMap<String, Object> map = new HashMap<>();
		String user_id = account.getUser_id();
		if (accountRepo.findByUserid(user_id) == null) {
			map = new HashMap<>();
			if (user_id.contains("admin")) {
				account.setRole("ROLE_ADMIN");
			} else {
				account.setRole("ROLE_USER");
			}
			account.setUser_password(bcryptEncoder.encode(account.getUser_password()));
			accountRepo.save(account);
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("message", "duplicated user_id");
		}
		return map;
	}
	
	//아이디 중복 검사
	public boolean checkID(String user_id) {
		Account acc = accountRepo.findAccountByUserId(user_id);
		if(acc==null)
			return true;
		return false;
	}

	// 로그인
	public HashMap<String, Object> login(String id, String pwd) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, pwd));
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("message", "authenticate ERROR");
			map.put("success", false);
			return map;
		}

		UserDetails userDetails = jwtUserDetailService.loadUserByUsername(id);
		Collection<? extends GrantedAuthority> c = userDetails.getAuthorities();
		String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
		String refreshToken = jwtTokenUtil.generateRefreshToken();
		// generate Token and save in redis
		Account user = accountRepo.findAccountByUserId(id);
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		TotToken retok = TotToken.builder().refreshToken(refreshToken).build();
		vop.set(id, retok, Long.parseLong(JWT_REFRESH_TOKEN_VALIDITY) * 1000, TimeUnit.MILLISECONDS);
		Account ac = Account.builder().user_id(id).role(user.getRole()).build();
		retok = TotToken.builder().account(ac).build();
		System.out.println("JWT_ACCESS_TOKEN_VALIDITY : " + JWT_ACCESS_TOKEN_VALIDITY);
		vop.set(accessToken, retok, Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000, TimeUnit.MILLISECONDS);
		map.put("success", true);
		map.put("accessToken", accessToken);
		map.put("refreshToken", refreshToken);
		Date expireTime =  new Date(System.currentTimeMillis() + Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000);
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		String expireTime_fotmat1 = format1.format(expireTime);
		map.put("expireTime", expireTime_fotmat1);
		return map;
	}
	
	public HashMap<String, Object> changPW(Account account){
		String e_password = bcryptEncoder.encode(account.getUser_password());
		account.setUser_password(e_password);
		accountRepo.updateUserPasswordWithUserid(account.getUser_id(), account.getUser_password());
		HashMap<String, Object> result = new HashMap<String, Object>();
		Account temp=accountRepo.findAccountByUserId(account.getUser_id());
		if(temp.getUser_password().equals(e_password)) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}

	public HashMap<String, Object> createApply(String user_id, Apply apply) {
		apply.setUser_id(user_id);
		Apply app = applyRepo.save(apply);
		HashMap<String, Object> map = new HashMap<>();
		if (app == null)
			map.put("success", false);
		else
			map.put("success", true);
		return map;
	}
	
	public HashMap<String, Object> updateApply(String user_id, Apply apply) {
		apply.setUser_id(user_id);
		Apply app = applyRepo.save(apply);
		HashMap<String, Object> map = new HashMap<>();
		if (app == null)
			map.put("success", false);
		else
			map.put("success", true);
		return map;
	}

	public HashMap<String, Object> checkPhone(String phone, int number) {
		HashMap<String, Object> map = new HashMap<>();
		Message coolSMS = new Message(apiKey, apiSecret);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("to", phone); // 수신전화번호
		params.put("from", "01092128410"); // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
		params.put("type", "SMS");
		params.put("text", "[마.리.댕] 인증번호는" + "[" + number + "]" + "입니다.");
		params.put("app_version", "test app 1.2"); // application name and version
		try {
			JSONObject obj = (JSONObject) coolSMS.send(params);
			long result = (long) obj.get("success_count");
			if (result == 1) {
				map.put("success", true);
				map.put("number", number);
			} else {
				map = null;
			}
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
			map = null;
		}
		return map;
	}

	public HashMap<String, Object> refreshToken(String accessToken, String refreshToken) {
		HashMap<String, Object> response = new HashMap<>();
		String user_id = null, refreshTokenFromDb = null;
		try {
			user_id = jwtTokenUtil.getUsernameFromToken(accessToken);
		} catch (IllegalArgumentException e) {
			System.out.println("IllegalArgumentException");
		} catch (ExpiredJwtException e) { // expire됐을 때
			user_id = e.getClaims().getSubject();
		}

		if (refreshToken != null) { // refresh를 같이 보냈으면.
			try {
				ValueOperations<String, Object> vop = redisTemplate.opsForValue();
				TotToken result = (TotToken) vop.get(user_id); // 얘는 refreshToken
				refreshTokenFromDb = result.getRefreshToken();
			} catch (IllegalArgumentException e) {
			}
			// 둘이 일치하고 만료도 안됐으면 재발급 해주기.
			if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
				final UserDetails userDetails = userDetailService.loadUserByUsername(user_id);
				String new_accessToken = jwtTokenUtil.generateAccessToken(userDetails);
				Account user = accountRepo.findAccountByUserId(user_id);
				ValueOperations<String, Object> vop = redisTemplate.opsForValue();
				Account ac = Account.builder().user_id(user_id).role(user.getRole()).build();
				TotToken token = TotToken.builder().account(ac).build();
				vop.set(new_accessToken, token, Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY), TimeUnit.SECONDS);
				response.put("success", true);
				response.put("accessToken", new_accessToken);
				Date expireTime =  new Date(System.currentTimeMillis() + Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY) * 1000);
				SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
				String expireTime_fotmat1 = format1.format(expireTime);
				response.put("expireTime", expireTime_fotmat1);
			} else {
				response.put("success", false);
				response.put("msg", "refresh token is expired.");
			}
		} else { // refresh token이 없으면
			response.put("success", false);
			response.put("msg", "your refresh token does not exist.");
		}

		return response;
	}

	public HashMap<String, Object> readAdopt() {
//		List<Apply> list = applyRepo.selectList();
		List<Apply> list = applyRepo.findAll();
		List<Apply> result = new ArrayList<Apply>();
		for(int i=0;i<list.size();i++) {
			Apply app = list.get(i);
			Apply temp = Apply.builder().
					uid(app.getUid()).
					regtime(app.getRegtime()).
					title(app.getTitle()).
					user_id(app.getUser_id()).build();
			result.add(temp);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		return map;
	}

	public Apply readAdopt(long uid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		return applyRepo.selectByuid(uid);
	}

}
