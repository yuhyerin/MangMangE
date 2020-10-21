package com.daeng.nyang.dto;

import javax.persistence.Id;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@RedisHash("loginUser")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
	
	@Id
	String id;
	
	private String user_name;
	private String user_id;

}
