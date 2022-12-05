package com.daeng.nyang.service.signup;


import com.daeng.nyang.controller.dto.AuthNumberCheckResponseDto;
import com.daeng.nyang.controller.dto.EmailCheckResponseDto;

public interface SignupService {
	public EmailCheckResponseDto checkEmail(String email);

	public AuthNumberCheckResponseDto checkAuthNumber(String authNumber, String hashNumber);
}
