package com.daeng.nyang.service.findid;

import com.daeng.nyang.dto.Account;

public interface FindUserIdService {
	
	/** 사용자 이름, 이메일로  아이디 찾기 */
	public String findUserId(String user_name, String user_email);
	
	/** 사용자 아이디, 이메일로  사용자 찾기 */
	public Account findUserByUserIdAndEmail(String user_id, String user_email);
}
