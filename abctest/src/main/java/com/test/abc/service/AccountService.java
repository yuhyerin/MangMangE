package com.test.abc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.abc.dto.Account;
import com.test.abc.jwt.JwtTokenProvider;
import com.test.abc.repo.AccountRepo;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
<<<<<<< HEAD
=======
	public static final long JWT_ACCESS_TOKEN_VALIDITY = 10 * 60; // 10분
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 24 * 60 * 60 * 7; // 일주일
>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
	
	public Map<String, Object> signup(Account account){
		System.out.println("SERVICE : "+account.toString());
		Map<String, Object> result = null;
		try {
			Account temp = accountRepo.findAccountByUserId(account.getUser_id());
			if(temp==null) {
				if("admin".equals(account.getUser_id()))
					account.setRole("ROLE_ADMIN");
				else
					account.setRole("ROLE_USER");
				System.out.println(account.toString());
				account.setUser_password(passwordEncoder.encode(account.getUser_password()));
				accountRepo.save(account);
				result = new HashMap<String, Object>();
				result.put("success", HttpStatus.OK);
			}
		} catch(NullPointerException e) {
			System.out.println("NULL POINT");
			if("admin".equals(account.getUser_id()))
				account.setRole("ROLE_ADMIN");
			else 
				account.setRole("ROLE_USER");
			System.out.println(account.toString());
			String ePassword = passwordEncoder.encode(account.getUser_password());
			System.out.println(ePassword);
			account.setUser_password(ePassword);
			System.out.println(account.toString());
			accountRepo.save(account);
			result = new HashMap<String, Object>();
			result.put("success", HttpStatus.OK);
		}
		return result;
	}
	
	public String login(String id, String password){
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		System.out.println("SERVICE : "+id+"\n"+password);
		Map<String, Object> result = null;
		String token = null;
		String refreshToken = null;
		Account temp = accountRepo.findAccountByUserId(id);
		if(temp!=null) {
			if(passwordEncoder.matches(password, temp.getUser_password())) {
				token = jwtTokenProvider.createToken(id);
<<<<<<< HEAD
				refreshToken = jwtTokenProvider.createRefreshToken(id);	//refreshtoken은 redis에 저장
				vop.set(id, refreshToken);
=======
				refreshToken = jwtTokenProvider.createRefreshToken();	//refreshtoken은 redis에 저장
				vop.set(token, id, JWT_ACCESS_TOKEN_VALIDITY);
				vop.set(id, refreshToken, JWT_REFRESH_TOKEN_VALIDITY);
>>>>>>> 0d6b9e53ee5c962574d19942adae93c798e1746f
			}
		}
		return token;
	}

	

}
