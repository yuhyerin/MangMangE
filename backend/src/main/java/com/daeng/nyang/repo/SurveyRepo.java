package com.daeng.nyang.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Survey;

@Repository
public interface SurveyRepo extends JpaRepository<Survey, Integer> {
//	@Query(value="select * from survey where user_id=:user_id", nativeQuery = true)
//	Survey findSurveyByUserid(String user_id);
	@Query(value="select * from survey where user_id=:user_id", nativeQuery = true)
	Optional<Survey> findSurveyByUserId(String user_id);
}