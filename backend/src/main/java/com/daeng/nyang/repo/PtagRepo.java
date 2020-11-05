package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daeng.nyang.dto.Ptag;

public interface PtagRepo extends JpaRepository<Ptag, String> {

	@Query(value = "insert into ptag(desertion_no,tagname) values(:desertion_no, :tagname)", nativeQuery = true)
	void insertPtag(Long desertion_no, String tagname);

	@Query(value = "select tagname from ptag p where p.desertion_no=:desertion_no", nativeQuery = true)
	String[] findTagNameByDesertionNo(Long desertion_no);

}
