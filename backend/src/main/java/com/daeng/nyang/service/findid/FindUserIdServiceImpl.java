package com.daeng.nyang.service.findid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class FindUserIdServiceImpl implements FindUserIdService{
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Override
	public String findUserId(String user_name, String user_email) {
		
		return accountRepo.findUserIdByUserNameAndUserEmail(user_name, user_email);
	}

	@Override
	public Account findUserByUserIdAndEmail(String user_id, String user_email) {
		
		return accountRepo.findUserByUserIdAndUserEmail(user_id, user_email);
	}

}
