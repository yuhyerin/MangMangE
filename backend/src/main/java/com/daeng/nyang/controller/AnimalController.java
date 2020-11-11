package com.daeng.nyang.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.AnimalLike;
import com.daeng.nyang.dto.AnimalListFE;
import com.daeng.nyang.dto.Survey;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.service.animal.AnimalService;
import com.daeng.nyang.service.survey.SurveyService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class AnimalController {
	@Autowired
	private SurveyService surveyService;
	@Autowired
	private AnimalService animalService;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@GetMapping(path = "/newuser/animal/allread")
	@ApiOperation(value = "(비회원) 전체동물조회")
	public ResponseEntity<HashMap<String, Object>> allread() {
		try {
			HashMap<String, Object> map = new HashMap<>();
			List<AnimalListFE> animalList = animalService.allAnimalRead();
			map.put("animalList", animalList);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping(path = "/user/animal/allread")
	@ApiOperation(value = "(회원) 전체동물조회")
	public ResponseEntity<HashMap<String, Object>> allreadUser(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		try {
			TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
			String user_id = user.getAccount().getUser_id();
			HashMap<String, Object> map = new HashMap<>();
			List<AnimalListFE> animalList = animalService.allAnimalRead(user_id);
			map.put("animalList", animalList);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping(path = "/user/animal/like")
	@ApiOperation("즐겨찾는 동물 조회")
	public ResponseEntity<HashMap<String, Object>> animalLikeList(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
		String user_id = user.getAccount().getUser_id();
		HashMap<String, Object> map = new HashMap<>();
		List<AnimalListFE> animalList = animalService.animalLikeList(user_id);
		map.put("animalList", animalList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@GetMapping(path = "/user/animal/surveyread")
	@ApiOperation(value = "설문기록여부확인")
	public ResponseEntity<HashMap<String, Object>> surveyread(HttpServletRequest request) {
		// 프론트에서 토큰을 받아오면 설문기록여부 반환
		String token = request.getHeader("Authorization");
		TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
		String user_id = user.getAccount().getUser_id();
		try {
			HashMap<String, Object> map = new HashMap<>();
			if (user_id == null) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			} else {
				Survey survey = surveyService.findSurveyByUserid(user_id);
				if (survey == null)
					map.put("survey", null);
				else
					map.put("survey", survey);
			}
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}

	@GetMapping(path = "/user/animal/matchlist")
	@ApiOperation(value = "매칭된동물리스트")
	public ResponseEntity<HashMap<String, Object>> matchlist(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // 토큰받기
		TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
		String user_id = user.getAccount().getUser_id();
		try {
			HashMap<String, Object> map = new HashMap<>();
			if (user_id == null) {
				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
			} else {
				Survey survey = null;
				survey = surveyService.findSurveyByUserid(user_id); // 토큰을 통해 얻은 유저아이디로 이 유저가 설문을 한적있는지 검사.
				if (survey == null) {
					map.put("survey", null);
					return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
				}
				// 유저의 MBTI
				// String userMbti = survey.getMbti(); // 설문을 한적 없다면 이 구문에서 에러가 나서 NOT_FOUND상태가
				// 반환됨.

				// 설문결과 유저에게 어울리는 강아지의 MBTI
				String userDogMbti = survey.getAnswer();

				// 추천율 100%
				// 설문결과 유저에게 어울리는 강아지의 MBTI를 똑같이 가지고 있는 강아지 리스트 반환.
				List<AnimalListFE> perfectlist = animalService.findAnimalByMbti(user_id, userDogMbti);
				map.put("perfect", perfectlist);

				// 추천율 75%
				// 설문결과 유저에게 어울리는 강아지의 MBTI 중 첫번째 글자 불일치 234
				String text1 = "";
				if (userDogMbti.indexOf("Q") == 0) { // Q로 시작하는 경우
					text1 = userDogMbti.replace('Q', 'E');
				} else { // E로 시작하는 경우
					text1 = userDogMbti.replace('E', 'Q');
				}
				List<AnimalListFE> goodlist1 = animalService.findAnimalByMbti(user_id, text1);

				// 설문결과 유저에게 어울리는 강아지의 MBTI 중 두번째 글자 불일치 134
				String text2 = "";
				if (userDogMbti.indexOf("C") == 1) { // 2번째 글자가 C인 경우
					text2 = userDogMbti.replace('C', 'F');
				} else { // 2번째 글자가 F인 경우
					text2 = userDogMbti.replace('F', 'C');
				}
				List<AnimalListFE> goodlist2 = animalService.findAnimalByMbti(user_id, text2);
				// map.put("good_2", goodlist2);

				// 설문결과 유저에게 어울리는 강아지의 MBTI 중 세번째 글자 불일치 124
				String text3 = "";
				if (userDogMbti.indexOf("S") == 2) { // 3번째 글자가 S인 경우
					text3 = userDogMbti.replace('S', 'I');
				} else { // 3번째 글자가 I인 경우
					text3 = userDogMbti.replace('I', 'S');
				}
				List<AnimalListFE> goodlist3 = animalService.findAnimalByMbti(user_id, text3);
				// map.put("good_3", goodlist3);

				// 설문결과 유저에게 어울리는 강아지의 MBTI 중 네번째 글자 불일치 123
				String text4 = "";
				if (userDogMbti.indexOf("W") == 3) { // 4번째 글자가 W인 경우
					text4 = userDogMbti.replace('W', 'A');
				} else { // 4번째 글자가 A인 경우
					text4 = userDogMbti.replace('A', 'W');
				}
				List<AnimalListFE> goodlist4 = animalService.findAnimalByMbti(user_id, text4);
				// map.put("good_4", goodlist4);

				// good 리스트 합쳐서 하나의 good 리스트로 만들기.
				List<AnimalListFE> goodlist = new ArrayList<>();
				goodlist.addAll(goodlist1);
				goodlist.addAll(goodlist2);
				goodlist.addAll(goodlist3);
				goodlist.addAll(goodlist4);

				map.put("good", goodlist);
			}
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}

	@PostMapping(path = "/user/animal/animalLike")
	@ApiOperation(value = "동물 좋아요")
	public ResponseEntity<HashMap<String, Object>> animalLike(@RequestBody AnimalLike animalLike,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // 토큰받기
		TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
		String user_id = user.getAccount().getUser_id();
		HashMap<String, Object> map = new HashMap<>();
		try {
			animalLike.setUser_id(user_id); // 여기에 토큰으로 받아온 유저 아이디를 대신 넣는다.
			AnimalLike new_animalLike = animalService.findAnimalLike(animalLike.getUser_id(),
					animalLike.getDesertion_no());
			if (new_animalLike != null) { // 있는 경우 [삭제]
				animalService.deleteAnimalLike(new_animalLike.getUser_id(), new_animalLike.getDesertion_no());
				map.put("like", false);
			} else { // 없는 경우 [생성]
				new_animalLike = animalService.join(animalLike);
				if (new_animalLike == null) {
					return new ResponseEntity<>(null, HttpStatus.CONFLICT);
				} else {
					map.put("like", true);
				}
			}
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (NullPointerException e) {
			e.printStackTrace();
			AnimalLike new_animalLike = null;
			new_animalLike = animalService.join(animalLike);
			if (new_animalLike == null) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			} else {
				map.put("like", true);
				return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
			}
		}
	}
}