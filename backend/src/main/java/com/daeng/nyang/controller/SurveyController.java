package com.daeng.nyang.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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

	@PostMapping(path = "/user/survey/create")
	@ApiOperation(value = "설문결과저장")
	public ResponseEntity<HashMap<String, Object>> surveyCreate(@RequestBody Survey survey,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // 토큰받기
//		System.out.println(token); // 받은 토큰 출력, 확인
//		System.out.println(survey.toString()); // 받은 survey값 출력, 확인
		if (token == null) {
			return null;
		} else if (jwtTokenUtil.isTokenExpired(token)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			try {
				HashMap<String, Object> map = new HashMap<>();
				// 설문 저장 로직 작성
//				System.out.println("Survey Create Controller");

				// 토큰으로 유저 아이디 받아오는 구문 필요
				// no.1
				String user_id = jwtTokenUtil.getUsernameFromToken(token);
//				System.out.println("user_id : " + user_id);

				// no.2
//			Map<String, Object> now_user = new HashMap<>();
//			now_user = jwtTokenUtil.getUserParseInfo(now_user_token);
//			String user_id2 = (String) now_user.get("user_id");
//			System.out.println("user_id2 : " + user_id2);

				survey.setUser_id(user_id); // 여기에 토큰으로 받아온 유저 아이디를 대신 넣는다. no.1
//			survey.setUser_id(user_id2); // 여기에 토큰으로 받아온 유저 아이디를 대신 넣는다. no.2
				Survey saveSurvey = surveyService.join(survey);
				map.put("message", "설문결과 저장 성공");
				map.put("saveSurvey", saveSurvey);
				return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
			} catch (Exception e) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
	}
}
