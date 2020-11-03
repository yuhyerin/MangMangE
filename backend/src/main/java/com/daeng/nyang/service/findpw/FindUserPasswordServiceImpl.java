package com.daeng.nyang.service.findpw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.service.changepassword.ChangePasswordService;
import com.daeng.nyang.service.email.EmailService;

@Service
public class FindUserPasswordServiceImpl implements FindUserPasswordService{

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@Override
	public void sendTempPasswordbyEmail(String user_id, String user_email) {
		
		// 임시비밀번호 발송 
		String temp_password = emailService.sendTempPassword(user_email);
		
		// 비번변경
		changePasswordService.updatePassword(user_id, temp_password);
	}
	
}
