package com.daeng.nyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@GetMapping("/apitest2")
	@ApiOperation("테스트")
	public String callApiHttp2() {
		
		System.out.println("테스트입니다!");
		return "OK";
	}

}
