package com.daeng.nyang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.daeng.nyang.model.Member;

@Mapper
public interface MemberMapper {
	
	Member login(Member member);
	Member findPwd(Member email);
	public int signUp(Member member);
	Member search(String userid);
	public List<Member> searchAll();
}
