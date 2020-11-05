package com.daeng.nyang.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Apply;

@Repository
public interface ApplyRepo extends JpaRepository<Apply, String>{

	@Query(value="select uid, user_id, title, regtime from apply", nativeQuery=true)
	List<Apply> selectList();
	
	@Query(value="select * from apply where uid=:uid", nativeQuery=true)
	Apply selectByuid(long uid);
}
