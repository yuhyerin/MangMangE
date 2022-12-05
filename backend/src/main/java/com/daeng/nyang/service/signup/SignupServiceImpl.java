package com.daeng.nyang.service.signup;

import com.daeng.nyang.common.ResponseCode;
import com.daeng.nyang.controller.dto.*;
import com.daeng.nyang.exception.UserAlreadyExistException;
import com.daeng.nyang.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daeng.nyang.entity.Account;
import com.daeng.nyang.repo.AccountRepo;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

	private final EmailService emailService;
	private final PasswordEncoder bcryptEncoder;
	private final AccountRepo accountRepo;
	private static final String salt = BCrypt.gensalt();

	// 회원가입
	public AccountResponseDto signup(AccountRequestDto account) {
		String user_id = account.getUser_id();
		Optional<Account> findAccount = accountRepo.findByUserId(user_id);
		if (findAccount.isPresent()) {
			throw new UserAlreadyExistException(ResponseCode.USER_ALREADY_EXIST);
		} else {
			if (user_id.contains("admin")) {
				account.setRole("ROLE_ADMIN");
			} else {
				account.setRole("ROLE_USER");
			}
			account.setUser_password(bcryptEncoder.encode(account.getUser_password()));
			Account result = accountRepo.save(Account.builder()
					.id(account.getId())
					.userId(account.getUser_id())
					.userPassword(account.getUser_password())
					.userEmail(account.getUser_email())
					.userName(account.getUser_name())
					.role(account.getRole())
					.build());
			return AccountResponseDto.builder()
					.id(result.getId())
					.user_id(result.getUserId())
					.user_email(result.getUserEmail())
					.user_name(result.getUserName())
					.role(result.getRole())
					.build();
		}
	}

	//아이디 중복 검사
	public IdCheckResponseDto checkID(String userId) {
		Optional<Account> findAccount = accountRepo.findByUserId(userId);
		boolean available = true;
		if(findAccount.isPresent()) available = false;
		return IdCheckResponseDto.builder()
				.available(available)
				.build();
	}

	// 이메일 중복 검사 & 인증번호 전송
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

	// 인증번호 검사
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
