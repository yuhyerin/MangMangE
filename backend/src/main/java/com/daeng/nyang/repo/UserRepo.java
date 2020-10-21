package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
	@Query("select * from user where user_id=:user_id and user_password=:user_password")
	public User loginUser(String user_id, String user_password);

}
