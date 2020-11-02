package com.daeng.nyang.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daeng.nyang.service.openapi.AnimalOpenApiService;

import io.swagger.annotations.ApiOperation;

@RestController
public class AnimalOpenAPIController {

	@Autowired
	private AnimalOpenApiService animalService;
	
	@GetMapping("/apitest")
	@ApiOperation("오픈API 동물목록 가져오기")
	public String callApiHttp() {
		
//		System.out.println("AnimalOpenApi컨트롤러에서 insertAnimalData()호출함");
//		animalService.insertAnimalData();
		
		System.out.println("AnimalOpenApi컨트롤러에서 parsingXmlData()호출함");
		animalService.parsingXmlData();
		return "OK";
	}
	
	@GetMapping("/newuser/apitest2")
	@ApiOperation("테스트")
	public ResponseEntity<?> callApiHttp2() {
		String message = "아무나 들어올 수 있는 /newuser/apitest2 테스트입니다!";
		System.out.println(message);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	@GetMapping("/user/apitest2")
	@ApiOperation("테스트")
	public ResponseEntity<?> callApiHttp3(HttpServletRequest request) {
		if(request.getHeader("Authorization")==null) {
			System.out.println("null임");
		}else {
			System.out.println("null아님");
		}
		String message = "권한이 있어야만 들어올 수 있는  /user/apitest2 테스트입니다!";
		System.out.println(message);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
	
	@GetMapping("/admin/test")
	@ApiOperation("관리자 전용")
	public ResponseEntity<?> test(HttpServletRequest request) {
		if(request.getHeader("Authorization")==null) {
			System.out.println("null임");
		}else {
			System.out.println("null아님");
		}
		String message = "관리자 전용 /admin/test";
		System.out.println(message);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}
