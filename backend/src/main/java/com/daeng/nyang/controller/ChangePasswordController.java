package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ChangePasswordController {

	// 비밀번호 변경
	@PostMapping(path = "/newuser/changepw")
	public ResponseEntity<?> findUserId(@RequestBody Map<String, String> User) {
		Map<String, String> resultMap = new HashMap<String, String>();
		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);

	}

}
