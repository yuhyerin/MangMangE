package com.daeng.nyang.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.AnimalListFE;
import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.service.animal.AnimalService;
import com.daeng.nyang.service.user.AccountService;

@RestController
@CrossOrigin("*")
public class AnimalDetailController {

	@Autowired
	private AnimalService animalService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// 비회원
	@GetMapping("/newuser/animal/detail")
	public ResponseEntity<HashMap<String, Object>> animalDetail(@RequestParam Long desertion_no) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		AnimalListFE animal = animalService.animalDetail(desertion_no); // animal 정보
		if (animal != null)
			map.put("animalList", animal);
		if (map.size() == 0)
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 회원 : 동물 상세 정보 조회
	@GetMapping("/user/animal/detail")
	public ResponseEntity<HashMap<String, Object>> animalDetailUser(@RequestParam Long desertion_no,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		TotToken user = (TotToken) redisTemplate.opsForValue().get(token);
		String user_id = user.getAccount().getUser_id();
		HashMap<String, Object> map = null;
		AnimalListFE animal = animalService.animalDetail(user_id, desertion_no); // animal 정보
		if (animal != null) {
			map = new HashMap<String, Object>();
			map.put("animalList", animal);
		}
		if(animalService.findApply(desertion_no, user_id))
			map.put("adoptCheck", true);
		else
			map.put("adoptCheck", false);
		if (map.size() == 0)
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
