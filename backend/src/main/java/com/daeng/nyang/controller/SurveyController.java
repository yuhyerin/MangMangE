package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Survey;
import com.daeng.nyang.jwt.JwtTokenUtil;
import com.daeng.nyang.service.survey.SurveyService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class SurveyController {
	private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@PostMapping(path = "/survey/create")
	@ApiOperation(value = "설문결과저장")
	public ResponseEntity<HashMap<String, Object>> surveyCreate(@RequestBody Survey survey) {
		System.out.println("survey_controller");
		try {
			HashMap<String, Object> map = new HashMap<>();
			//설문 저장 로직 작성
			System.out.println("Survey Create Controller");
			String now_user_token = survey.getToken();
			
			// 토큰으로 유저 아이디 받아오는 구문 필요
			// no.1
			String user_id = jwtTokenUtil.getUsernameFromToken(now_user_token);
			System.out.println("user_id : " + user_id);
			
			// no.2
//			Map<String, Object> now_user = new HashMap<>();
//			now_user = jwtTokenUtil.getUserParseInfo(now_user_token);
//			String user_id2 = (String) now_user.get("user_id");
//			System.out.println("user_id2 : " + user_id2);
			
			survey.setToken(null); // 설문조사 데이터베이스에 저장할 토큰초기화(토큰은 바뀔 수 있으니까.)
			survey.setUser_id(user_id); // 여기에 토큰으로 받아온 유저 아이디를 null 대신 넣는다. no.1
//			survey.setUser_id(user_id2); // 여기에 토큰으로 받아온 유저 아이디를 null 대신 넣는다. no.2
			surveyService.join(survey);
			map.put("설문결과 저장 성공", "Create success");

			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}