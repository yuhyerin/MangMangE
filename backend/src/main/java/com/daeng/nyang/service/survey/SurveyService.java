package com.daeng.nyang.service.survey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Survey;
import com.daeng.nyang.repo.SurveyRepo;

@Service
public class SurveyService {

	@Autowired
	private SurveyRepo surveyRepo;
	
	// 설문저장
	public Survey join(Survey survey) {
        System.out.println("Survey Create Service");
        return surveyRepo.save(survey);
    }
	
	// 유저 아이디를 통해 설문정보 가져오기.
	public Survey findSurveyByUserid(String user_id) {
		System.out.println("find Survey By Userid Service");
		return surveyRepo.findSurveyByUserid(user_id);
	}
}
