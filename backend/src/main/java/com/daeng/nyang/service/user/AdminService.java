package com.daeng.nyang.service.user;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daeng.nyang.dto.AnimalVideo;
import com.daeng.nyang.repo.AnimalVideoRepo;

@Service
public class AdminService {

	@Autowired
	private AnimalVideoRepo animalVideoRepo;
	
	public HashMap<String, Object> findNO(Long desertion_no){
		HashMap<String, Object> map = new HashMap<>();
		Optional<AnimalVideo> result = animalVideoRepo.findByDesertionNo(desertion_no);
		if(result.isPresent())	// 있으면 true
			map.put("success", true);
		else map.put("success", false);	// 없으면 false
		
		return map;
			
	}
	
	public HashMap<String, Object> findFile(String file){
		HashMap<String, Object> map = new HashMap<String, Object>();
		Optional<AnimalVideo> test = animalVideoRepo.findByFilepath(file);
		if(test.isPresent())
			map.put("success", true);
		else map.put("success", false);
		return map;
	}

	
	public HashMap<String, Object> uploadVideo(Map<String, Object> video, String user_id) {
		System.out.println("SERVICE START");
		HashMap<String, Object> map = new HashMap<>();
		String title = (String)video.get("title");
		String content = (String)video.get("content");
		Long desertion_no = Long.parseLong((String)video.get("desertion_no"));
		AnimalVideo av = AnimalVideo.builder().content(content).desertion_no(desertion_no)
				.title(title).writer(user_id).build();
		AnimalVideo result = animalVideoRepo.save(av);
		if (animalVideoRepo.findByDesertionNoAndTitle(desertion_no, title).isPresent()) {
			map.put("success", true);
			map.put("uid", result.getUid());
			System.out.println(map.toString());
		}
		else
			map.put("success", false);
		return map;
	}

	public HashMap<String, Object> uploadVideo(String accessToken, MultipartFile mfile) {
		System.out.println("SERVICE START");
		StringTokenizer originName = new StringTokenizer(mfile.getOriginalFilename(),"_");
		Long uid = Long.parseLong(originName.nextToken());
		String filename = originName.nextToken();
		System.out.println("origin NAME : "+originName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		AnimalVideo av = animalVideoRepo.findByUid(uid);
		try {
			String dest = "C:/SSAFY/git/s03p31b306/frontend/src/assets/videos/" + av.getDesertion_no()+"_"+filename;
			mfile.transferTo(new File(dest));
			av.setFilepath(dest);
			animalVideoRepo.save(av);
			System.out.println(animalVideoRepo.findByUid(uid).toString());
			map.put("success", true);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "영상 저장 실패");
		}
		return map;
	}

}
