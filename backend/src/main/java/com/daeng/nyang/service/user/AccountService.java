package com.daeng.nyang.service.user;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.repo.AccountRepo;
import com.daeng.nyang.repo.ApplyRepo;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private ApplyRepo applyRepo;
	@Value("${apiKey}")
	private String apiKey;
	@Value("${apiSecret}")
	private String apiSecret;
	
	
	public ResponseEntity<HashMap<String, Object>> createApply(String user_id, Apply apply){
		System.out.println("accountService 입장");
		System.out.println(apply.toString());
		apply.setUser_id(user_id);
		System.out.println(apply.toString());
		applyRepo.save(apply);
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", true);
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	public ResponseEntity<HashMap<String, Object>> checkPhone(String phone, int number){
		HashMap<String, Object> map = new HashMap<>();
		System.out.println("apiKey : "+apiKey);
		System.out.println("apiSecret : "+apiSecret);
		Message coolSMS = new Message(apiKey, apiSecret);
		
		HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone);    // 수신전화번호
        params.put("from", phone);    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[댕청냥청] 인증번호는" + "["+number+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version
        System.out.println(params.toString());
        try {
            JSONObject obj = (JSONObject) coolSMS.send(params);
            long result = (long)obj.get("success_count");
            System.out.println(result);
            if(result==1) {
            	map.put("success", true);
            	map.put("number", number);
            } else {
            	map = null;
            }
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            map = null;
        }
        if(map==null)
        	return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.BAD_REQUEST);
        else
        	return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
        	
	}

}
