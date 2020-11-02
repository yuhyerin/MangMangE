package com.daeng.nyang.service.findpw;

public interface FindUserPasswordService {
	
	void sendTempPasswordbyEmail(String user_id, String user_email);
}
