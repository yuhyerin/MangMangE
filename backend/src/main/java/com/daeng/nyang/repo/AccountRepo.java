package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daeng.nyang.entity.Account;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account, Long> {

//	@Query(value="select * from account where user_id=:user_id", nativeQuery = true)
	Optional<Account> findByUserId(String user_id);
	
	@Query(value="select user_id from account where user_name=:user_name and user_email = :user_email", nativeQuery = true)
	String findUserIdByUserNameAndUserEmail(String user_name, String user_email);
	
	@Query(value="select * from account where user_id=:user_id and user_email = :user_email", nativeQuery = true)
	Account findUserByUserIdAndUserEmail(String user_id, String user_email);

//	@Query(value="select * from account where user_email=:email", nativeQuery = true)
	Optional<Account> findUserByUserEmail(String email);
	
	@Query(value="update account set user_password = :temp_password where user_id=:user_id",nativeQuery=true)
	void updateUserPasswordWithUserid(String user_id, String temp_password);

}
