package com.daeng.nyang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@CrossOrigin("*")

public class MainController {
	// 스웨거 테스트용
	@GetMapping("/home")
	public String index() {
		return "index";
	}
}
