package com.daeng.nyang.dto;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@RedisHash
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotToken implements Serializable {
	
//	Token t;
	private static final long serialVersionUID = -7353484588260422449L;
//	private String user_id; //k
	private String refreshToken;	//v 
//	private String accessToken;	//k
	private Account account;		//v

}
