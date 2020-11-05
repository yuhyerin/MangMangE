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
        Survey newSurvey = new Survey();
        if(findSurveyByUserid(survey.getUser_id()) == null) {
        	return surveyRepo.save(survey);
        } else {
        	newSurvey = findSurveyByUserid(survey.getUser_id());
            newSurvey.setUser_id(survey.getUser_id());
            newSurvey.setMbti(survey.getMbti());
            newSurvey.setAnswer(survey.getAnswer());
            return surveyRepo.save(newSurvey);
        }
    }
	
	// 유저 아이디를 통해 설문정보 가져오기.
	public Survey findSurveyByUserid(String user_id) {
		System.out.println("FINDSURVEY");
		Survey surve = null;
		try {
		surve = surveyRepo.findSurveyByUserid(user_id);
		} catch(NullPointerException e) {
			System.out.println("NULL 잡잡");
			return null;
		}
		return surve;
	}
}
