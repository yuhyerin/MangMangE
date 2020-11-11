package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.service.findid.FindUserIdService;
import com.daeng.nyang.service.findpw.FindUserPasswordService;

@RestController
@CrossOrigin("*")
public class FindUserIdAndPasswordController {

	@Autowired
	private FindUserIdService findUserIdService;
	
	@Autowired
	private FindUserPasswordService findUserPasswordService;

	// 사용자 이름, 사용자 이메일로 아이디 찾기
	@PostMapping(path = "/newuser/findid")
	public ResponseEntity<?> findUserId(@RequestBody Map<String, String> User) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String user_name = User.get("user_name");
		String user_email = User.get("user_email");
		String user_id = findUserIdService.findUserId(user_name, user_email);
		resultMap.put("user_id", user_id);

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);

	}

	// 사용자 아이디, 사용자 이메일로 임시비번 발급하기
	@PostMapping(path = "/newuser/findpw")
	public ResponseEntity<?> findUserPassword(@RequestBody Map<String, String> User) {

		Map<String, String> resultMap = new HashMap<String, String>();

		String user_id = User.get("user_id");
		String user_email = User.get("user_email");
		Account account = findUserIdService.findUserByUserIdAndEmail(user_id, user_email);
		if(account==null) {
			resultMap.put("result", "fail");
			return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
		}
		// 임시 비번 전송 & 변경
		findUserPasswordService.sendTempPasswordbyEmail(user_id, user_email);
		resultMap.put("result", "success");
		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);

	}
}
