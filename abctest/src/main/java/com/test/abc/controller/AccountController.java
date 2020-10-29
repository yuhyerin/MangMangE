package com.test.abc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.abc.dto.Account;
import com.test.abc.jwt.TokenResponse;
import com.test.abc.service.AccountService;

@RestController
@CrossOrigin("*")
public class AccountController {
	
	@Autowired
	private AccountService accountService;

	
	@GetMapping("/home")
	public void test() {
		System.out.println("test");
	}
	
	@PostMapping("/signup")
	public Map<String, Object> signup(@RequestBody Account account){
		System.out.println(account.toString());
		Map<String, Object> result = accountService.signup(account);
		System.out.println(result.toString());
		return result;
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestParam String id, @RequestParam String password){
		String token = accountService.login(id, password);
		System.out.println(token);
		// bearer type의 토큰 생성 : Oauth2.0 표준
		ResponseEntity<TokenResponse> result = ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
		System.out.println(result.getBody().toString());
		return result;
	}

}
