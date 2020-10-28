package com.daeng.nyang.service.email;

public interface EmailService {
	
	/** 회원가입 시 이메일 인증을 위한 인증번호 전송 */
	String sendAuthEmail(String email);
}
