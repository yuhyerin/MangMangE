package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.daeng.nyang.entity.Account;

public interface FindUserIdPasswordRepo extends JpaRepository<Account, String>{

}
