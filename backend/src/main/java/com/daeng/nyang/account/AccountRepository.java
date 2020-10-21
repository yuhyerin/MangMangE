package com.daeng.nyang.account;

import org.springframework.data.repository.CrudRepository;

import com.daeng.nyang.dto.LoginUser;

public interface AccountRepository extends CrudRepository<LoginUser, String> {

}
