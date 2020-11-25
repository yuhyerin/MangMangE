package com.daeng.nyang.service.user;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalVideo;
import com.daeng.nyang.repo.AnimalRepo;
import com.daeng.nyang.repo.AnimalVideoRepo;

@Service
public class AdminService {

	@Autowired
	private AnimalVideoRepo animalVideoRepo;

	@Autowired
	private AnimalRepo animalRepo;

	@Value("${ubuntu.profile.upload.directory}")
//	@Value("${filePath}")
	private String filePath;

	public HashMap<String, Object> findNO(Long desertion_no) {
		HashMap<String, Object> map = new HashMap<>();
		Animal a = animalRepo.findAnimalByDesertionNo(desertion_no);
		if (a == null)
			map.put("success", false);
		else {
			map.put("success", true);
			map.put("image", a.getPopfile());
		}
		return map;

	}

	public HashMap<String, Object> findFile(String fileName) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Optional<AnimalVideo> test = animalVideoRepo.findByFilepath(fileName);
		if (test.isPresent())
			map.put("success", true);
		else
			map.put("success", false);
		return map;
	}

	public HashMap<String, Object> uploadVideo(Map<String, Object> video, String user_id) {
		HashMap<String, Object> map = new HashMap<>();
		String title = (String) video.get("title");
		String content = (String) video.get("content");
		String filepath = (String) video.get("filepath");
		if (filepath.equals("") || filepath == null) {
			map.put("success", false);
			return map;
		}
		Long desertion_no = Long.parseLong((String) video.get("desertion_no"));
		AnimalVideo av = AnimalVideo.builder().content(content).desertion_no(desertion_no).title(title).writer(user_id)
				.filepath(filepath).build();
		AnimalVideo result = animalVideoRepo.save(av);
		if (animalVideoRepo.findByDesertionNoAndTitle(desertion_no, title).isPresent()) {
			map.put("success", true);
			map.put("uid", result.getUid());
		} else
			map.put("success", false);
		return map;
	}

	public HashMap<String, Object> uploadVideo(String accessToken, MultipartFile mfile) {
		System.out.println("origin file name : " + mfile.getOriginalFilename());
		StringTokenizer originName = new StringTokenizer(mfile.getOriginalFilename(), "_");
//		Long uid = Long.parseLong(originName.nextToken());
//		System.out.println("length? : " + uid.toString().length());
//		System.out.println("file name is " + mfile.getOriginalFilename().substring(uid.toString().length()));
//		String filename = mfile.getOriginalFilename().substring(uid.toString().length() + 1);
		HashMap<String, Object> map = new HashMap<String, Object>();
//		AnimalVideo av = animalVideoRepo.findByUid(uid);
		try {
//			String dest = filePath + av.getDesertion_no()+"_"+filename;
			String dest = filePath + mfile.getOriginalFilename();
			mfile.transferTo(new File(dest));
//			if(animalVideoRepo.findByUid(uid).getFilepath()==null) {
//				map.put("success", false);
//				map.put("msg", "영상저장 실패");
//				animalVideoRepo.delete(av);
//			}
//			else {
			map.put("success", true);
//			}
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "영상 저장 실패");
		}
		return map;
	}

}
