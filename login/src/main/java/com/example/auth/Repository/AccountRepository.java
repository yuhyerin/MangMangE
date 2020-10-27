package com.example.auth.Repository;

import org.springframework.data.repository.CrudRepository;

import com.example.auth.Domain.Account;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Long deleteByUsername(String username);
}
