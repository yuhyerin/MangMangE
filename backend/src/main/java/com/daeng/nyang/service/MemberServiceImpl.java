package com.daeng.nyang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.mapper.MemberMapper;
import com.daeng.nyang.model.Member;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberMapper memberMapper;

	@Override
	public Member login(Member member) {
		return memberMapper.login(member);
	}
	
	@Override
	public int signUp(Member member) {
		return memberMapper.signUp(member);
	}
	
	@Override
	public Member findPwd(Member email) {
		return memberMapper.findPwd(email);
	}

	@Override
	public List<Member> searchAll() {		
		return memberMapper.searchAll();
	}

	@Override
	public Member search(String userid) {	
		return memberMapper.search(userid);
	}
}
