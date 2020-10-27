package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Survey;

@Repository
public interface SurveyRepo extends JpaRepository<Survey, Integer> {

}