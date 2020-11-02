package com.daeng.nyang.service.email;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${AUTH_SERVICE_EMAIL}")
	private String AUTH_SERVICE_EMAIL;
	
	@Override
	public String sendAuthEmail(String email){
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String auth_number = generateAuthNumber(); // 이메일 인증번호 생성
		StringBuilder sb = new StringBuilder();
		sb.append("<h3> 이메일 ");
		sb.append(email);
		sb.append("로 회원가입이 요청되었습니다. </h3>");
		sb.append("<p> 인증번호 ");
		sb.append("<b>"+auth_number+"</b>");
		sb.append(" 를 입력해주세요. </p>");
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
		try {
			helper.setText(sb.toString(), true);
			helper.setTo(email);
			mimeMessage.setFrom(AUTH_SERVICE_EMAIL);
			mimeMessage.setSubject("인증번호");
			mailSender.send(mimeMessage);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return auth_number;
	}
	
	@Override
	public String sendTempPassword(String user_email){
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String temp_password = generateAuthNumber(); // 임시비밀번호 생성 
		StringBuilder sb = new StringBuilder();
		sb.append("<h3> 이메일 ");
		sb.append(user_email);
		sb.append("로 임시비밀번호 발급이 요청되었습니다. </h3>");
		sb.append("<p> 임시비밀번호 : ");
		sb.append("<b>"+temp_password+"</b></p>");
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
		try {
			helper.setText(sb.toString(), true);
			helper.setTo(user_email);
			mimeMessage.setFrom(AUTH_SERVICE_EMAIL);
			mimeMessage.setSubject("마이댕댕 - 임시비밀번호 발급 ");
			mailSender.send(mimeMessage);
			
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return bcryptEncoder.encode(temp_password);
	}
	
	private String generateAuthNumber() {
		
		Random random = new Random();
		StringBuffer bf = new StringBuffer();
		// 8자리 대문자알파벳, 숫자로 이루어진 인증코드 생성
		for(int i=0; i<8; i++) {
			// true면 알파벳, false면 숫자
			if(random.nextBoolean()) {
				bf.append((char)((int)(random.nextInt(26)+65)));
			}else {
				bf.append((random.nextInt(10)));
			}
		}
		return bf.toString();
	}

}
