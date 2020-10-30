package com.daeng.nyang.service.animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.repo.AnimalRepo;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepo animalRepo;

	public List<Animal> findAnimalAll() {
		List<Animal> animal = animalRepo.findAnimalAll();
		return animal;
	}
	
	// 100퍼센트 일치. 추천율 100프로.
	public List<Animal> findAnimalByMbti(String mbti) {
		List<Animal> animal = animalRepo.findAnimalByMbti(mbti);
		return animal;
	}
	
	public List<Animal> findAnimalMbtiByKeyword(String mbti) {
		List<Animal> animal = animalRepo.findAnimalMbtiByKeyword(mbti);
		return animal;
	}
}
