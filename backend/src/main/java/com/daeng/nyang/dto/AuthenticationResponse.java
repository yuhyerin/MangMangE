package com.daeng.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
	
	    private String user_id;
	    private String user_name;
	    
}
