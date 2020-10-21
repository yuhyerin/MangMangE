package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.AuthenticationRequest;
import com.daeng.nyang.dto.AuthenticationResponse;
import com.daeng.nyang.service.user.JwtService;
import com.daeng.nyang.service.user.loginService;

@CrossOrigin(origins = { "*" })
@RestController
public class loginController {
	
	@Autowired
	loginService loginservice;
	@Autowired
	JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthenticationRequest user,
			HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			AuthenticationResponse loginUser = loginservice.login(user.getUser_id(), user.getUser_password());
			// 로그인 성공 했다면, 토큰을 생성한다.
			String token = jwtService.create(loginUser);
			response.setHeader("jwt-auth-token", token);
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	

}
