package com.daeng.nyang.service.findpw;

public interface FindUserPasswordService {
	
	public void sendTempPasswordbyEmail(String user_id, String user_email);
}
