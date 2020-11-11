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
		System.out.println("SERVICE START");
		return animalVideoRepo.findAnimalVideoAll();
	}
	public AnimalVideo detailvideo(Long uid) {
		System.out.println("SERVICE START");
		AnimalVideo animalVideo = animalVideoRepo.findByUid(uid);
		if (animalVideo != null)
			System.out.println(animalVideo.toString());
		return animalVideo;
	}
	
}
