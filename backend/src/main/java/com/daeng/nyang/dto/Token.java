package com.daeng.nyang.dto;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.Builder;
import lombok.Data;

@Data
@RedisHash
@Builder
public class Token {
	
	private static final long serialVersionUID = -7353484588260422449L;
//    private String user_id;
    private String refreshToken;

}
