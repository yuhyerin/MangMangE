package com.daeng.nyang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class AnimalOpenAPIController {

	@GetMapping("/apitest")
	@ApiOperation("오픈API 동물목록 가져오기")
	public String callApiHttp() {

		String SERVICE_KEY = "WqbaHzV11YAQJ3NIV3FjU9jO0QfwfGHBn2Dfgp6amhX9WVXx%2BEzjJcnZCNN0JEGvcqe6tVhLLCFoQBgy7xqajA%3D%3D";
		StringBuilder urlBuilder = new StringBuilder(
				"http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/abandonmentPublic?"
						+ "bgnde=20140301" + "&endde=20140430" + "&pageNo=1" + "&numOfRows=10"
						+ "&ServiceKey="); /* URL */

		try {
			System.out.println("원본: " + SERVICE_KEY);
			System.out.println("인코딩: " + URLEncoder.encode(SERVICE_KEY, "UTF-8"));
			System.out.println("디코딩: " + URLDecoder.decode(SERVICE_KEY, "UTF-8"));
//			SERVICE_KEY = URLEncoder.encode(SERVICE_KEY, "UTF-8");
			SERVICE_KEY = URLDecoder.decode(SERVICE_KEY, "UTF-8");
			System.out.println("최종보내는 키 : "+SERVICE_KEY);

			urlBuilder.append(SERVICE_KEY);
			URL url;
			try {
				url = new URL(urlBuilder.toString());
				HttpURLConnection conn;
				try {
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Content-type", "application/xml");
					System.out.println("Response code: " + conn.getResponseCode());
					BufferedReader rd;
					if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
						rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					} else {
						rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
					}
					StringBuilder sb = new StringBuilder();
					String line;
					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
					rd.close();
					conn.disconnect();
					System.out.println(sb.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "성공";
	}

}
