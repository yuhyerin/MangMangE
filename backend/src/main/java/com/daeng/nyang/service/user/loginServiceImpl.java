package com.daeng.nyang.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.AuthenticationResponse;
import com.daeng.nyang.dto.User;
import com.daeng.nyang.repo.UserRepo;

@Service
public class loginServiceImpl implements loginService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public AuthenticationResponse login(String user_id, String user_password) {
		User loginuser = userRepo.loginUser(user_id, user_password);
		if( loginuser!=null ) {
			AuthenticationResponse authuser = new AuthenticationResponse(loginuser.getUser_id(),
					loginuser.getUser_name());
			return authuser;
		}else {
			throw new RuntimeException("그런사람은 존재하지 않습니다!");
		}
	}

}
