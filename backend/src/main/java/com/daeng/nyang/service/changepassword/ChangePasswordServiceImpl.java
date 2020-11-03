package com.daeng.nyang.service.changepassword;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.repo.AccountRepo;

@Service
public class ChangePasswordServiceImpl implements ChangePasswordService{

	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public void updatePassword(String user_id, String temp_password) {
		
		accountRepo.updateUserPasswordWithUserid(user_id,temp_password);
	}
	
	
}
