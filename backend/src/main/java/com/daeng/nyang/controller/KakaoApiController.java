package com.daeng.nyang.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.daeng.nyang.dto.KakaoPayReadyVO;

import lombok.Setter;

@RestController
@CrossOrigin("*")
public class KakaoApiController {

	private static final String HOST = "https://kapi.kakao.com";

	private KakaoPayReadyVO kakaoPayReadyVO;

	@PostMapping("/newuser/kakaoPay")
	public String kakaoPay(@RequestBody Map<String, String> video) {
//		System.out.println("kakaoPay post............................................");
		RestTemplate restTemplate = new RestTemplate();

		int videoid = Integer.parseInt(video.get("videoid"));
		
		// 서버로 요청할 Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK " + "0d4df76238b82210be6a080dd8bb9c43");
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

		// 서버로 요청할 Body
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("cid", "TC0ONETIME");
		params.add("partner_order_id", "123");
		params.add("partner_user_id", "kakaodev");
		params.add("item_name", "item_name");
		params.add("quantity", "1");
		params.add("total_amount", "1000");
		params.add("tax_free_amount", "0");
		params.add("approval_url", "https://k3b306.p.ssafy.io/videos/0");
		params.add("cancel_url", "http://k3b306.p.ssafy.io/video/"+videoid);
		params.add("fail_url", "http://k3b306.p.ssafy.io/video/"+videoid);

		HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

		try {
			kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body,
					KakaoPayReadyVO.class);

//			System.out.println("" + kakaoPayReadyVO);

			return kakaoPayReadyVO.getNext_redirect_pc_url();

		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "실패~!";

	}

}
