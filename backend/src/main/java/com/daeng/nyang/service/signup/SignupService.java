package com.daeng.nyang.service.signup;


import com.daeng.nyang.controller.dto.*;

public interface SignupService {

	public AccountResponseDto signup(AccountRequestDto account);
	public IdCheckResponseDto checkID(String userId);
	public EmailCheckResponseDto checkEmail(String email);
	public AuthNumberCheckResponseDto checkAuthNumber(String authNumber, String hashNumber);


}
