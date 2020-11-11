package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daeng.nyang.dto.TotToken;
import com.daeng.nyang.service.user.AdminService;

@RestController
@CrossOrigin("*")
public class AdminController {
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private AdminService adminService;
	
	@GetMapping(path="/admin/upload/checkNO")
	public ResponseEntity<HashMap<String, Object>> checkNO(
			@RequestParam Long desertion_no){
		HashMap<String, Object> map = adminService.findNO(desertion_no);
		if((boolean)map.get("success"))
			return new ResponseEntity<>(map, HttpStatus.OK);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path="/admin/upload/checkFile")
	public ResponseEntity<HashMap<String, Object>> checkFile(
			@RequestParam String fileName){
		HashMap<String, Object> map = adminService.findFile(fileName);
		if((boolean)map.get("success"))
			return new ResponseEntity<>(map, HttpStatus.ACCEPTED);	//있으면 true
		return new ResponseEntity<>(map, HttpStatus.OK);	// 없으면 false
	}
	
	@PostMapping(path = "/admin/uploadVideo")
	public ResponseEntity<HashMap<String, Object>> uploadDBVideo(
			@RequestBody Map<String, Object> video,
			HttpServletRequest request) {
		TotToken user = (TotToken) redisTemplate.opsForValue().get(request.getHeader("Authorization"));
		HashMap<String, Object> map = adminService.uploadVideo(video, user.getAccount().getUser_id());
		if((boolean)map.get("success"))	// success 가 true이면 db에 저장 완료 false 이면 db에 저장 안됨
			return new ResponseEntity<>(map, HttpStatus.OK);
		return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
	}

	@PostMapping(path = "/admin/upload")
	public ResponseEntity<HashMap<String, Object>> upload(
			@RequestParam MultipartFile mfile,
			HttpServletRequest request) {
		HashMap<String, Object> map = new HashMap<>();
		if (mfile == null) {
			map.put("msg", "영상정보가 없습니다.");
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}
		map = adminService.uploadVideo(request.getHeader("Authorization"), mfile);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
