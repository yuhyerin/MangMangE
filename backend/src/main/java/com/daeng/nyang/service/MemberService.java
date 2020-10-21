package com.daeng.nyang.service;

import java.util.List;

import com.daeng.nyang.model.Member;

public interface MemberService {

	Member login(Member member);
	Member findPwd(Member email);
	public int signUp(Member member);
	Member search(String userid);
	public List<Member> searchAll();
}
