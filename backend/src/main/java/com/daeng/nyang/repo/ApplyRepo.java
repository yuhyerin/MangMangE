package com.daeng.nyang.repo;

import java.util.List;
import java.util.Optional;

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
	
	@Query(value="select * from apply where ani_num=:desertion_no and user_id=:user_id", nativeQuery=true)
	Optional<Apply> findApplyByDesertionNoAndUserId(long desertion_no, String user_id);
}
