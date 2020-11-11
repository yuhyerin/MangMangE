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
		animalService.parsingXmlData();
		return "OK";
	}
}
