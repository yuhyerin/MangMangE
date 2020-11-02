package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.daeng.nyang.dto.Account;

public interface AccountRepo extends CrudRepository<Account, Integer> {
	
	@Query(value="select * from account where user_id=:user_id", nativeQuery = true)
	Account findByUserid(String user_id);
	
	@Query(value="select user_id from account where user_name=:user_name and user_email = :user_email", nativeQuery = true)
	String findUserIdByUserNameAndUserEmail(String user_name, String user_email);
	
	@Query(value="select * from account where user_id=:user_id and user_email = :user_email", nativeQuery = true)
	Account findUserByUserIdAndUserEmail(String user_id, String user_email);
}
