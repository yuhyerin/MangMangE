package com.test.abc.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.abc.dto.Account;

@RestController
@CrossOrigin("*")
public class AccountController {
	
	@GetMapping("/home")
	public void test() {
		System.out.println("test");
	}
	
	@PostMapping("/signup")
	public Map<String, String> signup(@RequestBody Account account){
		System.out.println(account.toString());
		return null;
	}

}
