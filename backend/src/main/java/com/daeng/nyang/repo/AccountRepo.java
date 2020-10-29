package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.daeng.nyang.dto.Account;

public interface AccountRepo extends CrudRepository<Account, Integer> {
	
	@Query(value="select * from account where user_id=:user_id", nativeQuery = true)
	Account findByUserid(String user_id);

}
