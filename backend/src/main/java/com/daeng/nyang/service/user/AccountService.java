package com.daeng.nyang.service.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.daeng.nyang.repo.ApplyRepo;

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
	
	@Value("${JWT_ACCESS_TOKEN_VALIDITY}")
	private String JWT_ACCESS_TOKEN_VALIDITY;
	@Value("${JWT_REFRESH_TOKEN_VALIDITY}")
	private String JWT_REFRESH_TOKEN_VALIDITY;
	
	@Value("${apiKey}")
	private String apiKey;
	@Value("${apiSecret}")
	private String apiSecret;
	
	// 회원가입
	public HashMap<String, Object> signup(Account account){
		System.out.println("SERVICE START");
		HashMap<String, Object> map = new HashMap<>();
		String user_id = account.getUser_id();
		if (accountRepo.findByUserid(user_id) == null) {
			map = new HashMap<>();
			if ("admin".equals(user_id)) {
				account.setRole("ROLE_ADMIN");
			} else {
				account.setRole("ROLE_USER");
			}
			account.setUser_password(bcryptEncoder.encode(account.getUser_password()));
			System.out.println(account.toString());
			accountRepo.save(account);
			map.put("success", true);
		} else {
			map.put("success", false);
			map.put("message", "duplicated user_id");
		}
		System.out.println(map.toString());
		System.out.println("SERVICE END");
		return map;
	}
	
	// 로그인
	public HashMap<String, Object> login(String id, String pwd){
		System.out.println("SERVICE START");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, pwd));
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "authenticate ERROR");
			System.out.println("authenticate error");
			map.put("success", false);
			return map;
		}

		UserDetails userDetails = jwtUserDetailService.loadUserByUsername(id);
		System.out.println(userDetails.toString());
		Collection<? extends GrantedAuthority> c = userDetails.getAuthorities();
		System.out.println(c.toString());
		String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
		String refreshToken = jwtTokenUtil.generateRefreshToken();
		System.out.println("ACK : "+accessToken);
		System.out.println("REF : "+refreshToken);
		// generate Token and save in redis
		
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		TotToken retok = TotToken.builder().refreshToken(refreshToken).build();
		vop.set(id, retok, Long.parseLong(JWT_REFRESH_TOKEN_VALIDITY)*1000, TimeUnit.MILLISECONDS);
		Account ac = Account.builder().user_id(id).build();
		retok = TotToken.builder().account(ac).build();
		vop.set(accessToken, retok, Long.parseLong(JWT_ACCESS_TOKEN_VALIDITY)*1000, TimeUnit.MILLISECONDS);
		map.put("success", true);
		map.put("accessToken", accessToken);
		map.put("refreshToken", refreshToken);
		return map;
	}
	
	public ResponseEntity<HashMap<String, Object>> createApply(String user_id, Apply apply){
		System.out.println("accountService 입장");
		System.out.println(apply.toString());
		apply.setUser_id(user_id);
		System.out.println(apply.toString());
		applyRepo.save(apply);
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	public ResponseEntity<HashMap<String, Object>> checkPhone(String phone, int number){
		HashMap<String, Object> map = new HashMap<>();
		System.out.println("apiKey : "+apiKey);
		System.out.println("apiSecret : "+apiSecret);
		Message coolSMS = new Message(apiKey, apiSecret);
		
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", "01092128410");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[댕청냥청] 인증번호는" + "["+number+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version
        System.out.println(params.toString());
        try {
            JSONObject obj = (JSONObject) coolSMS.send(params);
            long result = (long)obj.get("success_count");
            System.out.println(result);
            if(result==1) {
            	map.put("success", true);
            	map.put("number", number);
            } else {
            	map = null;
            }
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            map = null;
        }
        if(map==null)
        	return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.BAD_REQUEST);
        else
        	return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
        	
	}

}
