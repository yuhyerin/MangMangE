package com.daeng.nyang.service.signup;

import com.daeng.nyang.controller.dto.AuthNumberCheckResponseDto;
import com.daeng.nyang.controller.dto.EmailCheckResponseDto;
import com.daeng.nyang.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.daeng.nyang.entity.Account;
import com.daeng.nyang.repo.AccountRepo;

import java.util.Optional;

@Slf4j
@Service
public class SignupServiceImpl implements SignupService {

	@Autowired
	private EmailService emailService;

	@Autowired
	private AccountRepo accountRepo;

	private static final String salt = BCrypt.gensalt();

	@Override
	public EmailCheckResponseDto checkEmail(String email) {

		Optional<Account> findAccount = accountRepo.findUserByUserEmail(email);
		boolean available = true;
		String hashNumber = null;
		if(findAccount.isPresent()){
			available = false;
		}else{
			// TODO 인증번호 이메일 전송 구현해야 함
//			String authNumber = emailService.sendAuthEmail(email); // 인증번호 전송
			String authNumber = "ABCDE";
			hashNumber = BCrypt.hashpw(authNumber, salt); // 인증번호 암호화
			log.debug(authNumber);
		}

		return EmailCheckResponseDto.builder()
				.email(email)
				.originHash(hashNumber)
				.available(available)
				.build();
	}

	@Override
	public AuthNumberCheckResponseDto checkAuthNumber(String authNumber, String hashNumber) {
		boolean result = false;
		if(BCrypt.checkpw(authNumber, hashNumber)){
			result = true;
		}
		return AuthNumberCheckResponseDto.builder()
				.result(result)
				.build();
	}

}
