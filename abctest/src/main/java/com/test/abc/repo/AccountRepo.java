package com.test.abc.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.abc.dto.Account;

@Repository
public interface AccountRepo extends CrudRepository<Account, Integer> {
	
	@Query(value="select * from account a where a.user_id=:user_id",nativeQuery = true)
	Account findAccountByUserId(String user_id);
}
