package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.daeng.nyang.dto.Account;

public interface FindUserIdPasswordRepo extends JpaRepository<Account, String>{

}
