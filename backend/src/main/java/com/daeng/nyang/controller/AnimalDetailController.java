package com.daeng.nyang.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.service.animal.AnimalService;

@RestController
@CrossOrigin("*")
public class AnimalDetailController {

	@Autowired
	private AnimalService animalService;

	@GetMapping("/newuser/animal/detail")
	public ResponseEntity<HashMap<String, Object>> animalDetail(@RequestParam Long desertion_no) {
		System.out.println("CONTROLLER START");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Animal animal = animalService.animalDetail(desertion_no);
		System.out.println(animal.toString());
		if(animal!=null)
			map.put("animal", animal);
		System.out.println(map.toString());
		List<String> personality = animalService.animalPersonality(desertion_no);
		System.out.println(personality.toString());
		if(personality!=null)
			map.put("personality", personality);
		System.out.println("CONTROLLER END");
		System.out.println(map.toString());
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
