package com.daeng.nyang.service.signup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class SignupServiceImpl implements SignupService {

	@Autowired
	private AccountRepo accountRepo;

	@Override
	public boolean checkEmail(String email) {

		Account user = accountRepo.findUserByEmail(email);
		boolean isAvailable = false;
		if (user == null) {
			isAvailable = true;
		}

		return isAvailable;
	}

}
