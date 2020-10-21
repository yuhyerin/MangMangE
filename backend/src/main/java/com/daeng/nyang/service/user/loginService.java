package com.daeng.nyang.service.user;

import com.daeng.nyang.dto.AuthenticationResponse;

public interface loginService {
	
	public AuthenticationResponse login(String user_id, String user_password);

}
