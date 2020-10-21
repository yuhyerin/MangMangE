package com.daeng.nyang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.daeng.nyang.account.AccountRepository;
import com.daeng.nyang.dto.LoginUser;

@Component
public class RedisRunner implements ApplicationRunner {

	@Autowired
	StringRedisTemplate redisTemplate;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ValueOperations<String, String> values = redisTemplate.opsForValue();
//		values.getAndSet("key", "1234");
		
//		Account account = new Account();
		LoginUser lu = LoginUser.builder()
				.user_id("user_id")
				.user_name("user_name")
				.build();
		
		accountRepository.save(lu);
		
//		Optional<Account> byId = accountRepository.findById(account.getUser_id());
//		System.out.println(byId.orElse(new Account()).getUser_name());
	}
	

}
