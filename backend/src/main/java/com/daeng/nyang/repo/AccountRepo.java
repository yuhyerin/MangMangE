package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.daeng.nyang.dto.Account;

public interface AccountRepo extends CrudRepository<Account, Long> {
	
	@Query(value="select * from account where user_id=:user_id", nativeQuery = true)
	Account findByUserid(String user_id);
	
	@Query(value="select * from account where user_email=:email", nativeQuery = true)
	Account findUserByEmail(String email);
	
	@Query(value="update account set user_password where user_email=:email",nativeQuery=true)
	void updateUserPasswordWithEmail(String email);
}
