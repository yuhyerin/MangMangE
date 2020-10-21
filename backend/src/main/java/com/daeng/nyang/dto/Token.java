package com.daeng.nyang.dto;

import java.io.Serializable;
import org.springframework.data.redis.core.RedisHash;
import lombok.Data;

@Data
@RedisHash
public class Token implements Serializable {
	
	private static final long serialVersionUID = -7353484588260422449L;
    private String username;
    private String refreshToken;

}
