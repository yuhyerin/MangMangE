package com.daeng.nyang.service.user;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	
	public ResponseEntity<HashMap<String, Object>> createApply(String user_id, Apply apply){
		System.out.println("accountService 입장");
		System.out.println(apply.toString());
		Account account = accountRepo.findByUserid(user_id);
		apply.setAccount(account);
		System.out.println(apply.toString());
		HashMap<String, Object> map = new HashMap<>();
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}

}
