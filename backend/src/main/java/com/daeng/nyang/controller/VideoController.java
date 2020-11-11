package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.AnimalVideo;
import com.daeng.nyang.service.video.VideoService;

@RestController
@CrossOrigin("*")
public class VideoController {

	@Autowired
	private VideoService videoService;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	// 모든 비디오
	@GetMapping("/newuser/video/allvideo")
	public ResponseEntity<HashMap<String, Object>> allvideo() {
		System.out.println("CONTROLLER START");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			List<AnimalVideo> videoList = videoService.allVideo();
			for(int i=0;i<videoList.size();i++) {
				AnimalVideo av = videoList.get(i);
				av.setFilepath(av.getFilepath().substring(16, av.getFilepath().length()-4));
				System.out.println(av.getFilepath());
			}
			map.put("VideoList", videoList);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}

	// 특정 비디오
	@GetMapping("/newuser/video/detailvideo")
	public ResponseEntity<HashMap<String, Object>> detailvideo(@RequestParam Long uid) {
		System.out.println("DETAILVIDEO CONTROLLER START");
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			AnimalVideo av = videoService.detailvideo(uid);
			av.setFilepath(av.getFilepath().substring(16, av.getFilepath().length()-4));
			map.put("VideoDetail", av);
			return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
		} catch (Exception e) {	
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
		}
	}
}
