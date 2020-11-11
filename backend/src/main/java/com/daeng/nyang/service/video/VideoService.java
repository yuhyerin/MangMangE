package com.daeng.nyang.service.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.AnimalVideo;
import com.daeng.nyang.repo.AnimalVideoRepo;

@Service
public class VideoService {

	@Autowired
	private AnimalVideoRepo animalVideoRepo;

	public List<AnimalVideo> allVideo() {
		return animalVideoRepo.findAnimalVideoAll();
	}
	public AnimalVideo detailvideo(Long uid) {
		AnimalVideo animalVideo = animalVideoRepo.findByUid(uid);
		return animalVideo;
	}
	
	public List<AnimalVideo> searchVideo(Long desertion_no){
		List<AnimalVideo> list = animalVideoRepo.findByDesertionNo(desertion_no);
		return list;
	}
	
}
