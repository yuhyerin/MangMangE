package com.daeng.nyang.service.animal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalLike;
import com.daeng.nyang.dto.Survey;
import com.daeng.nyang.repo.AnimalRepo;
import com.daeng.nyang.repo.AnimalLikeRepo;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepo animalRepo;

	@Autowired
	private AnimalLikeRepo animalLikeRepo;

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

	// 좋아요 저장
	public AnimalLike join(AnimalLike animalLike) {
		System.out.println("AnimalLike Create Service");
		System.out.println(animalLike.toString());
		AnimalLike result = animalLikeRepo.save(animalLike);
		System.out.println("RESULT : "+result.toString());
		return result;
	}

	// 유저 아이디를 통해 좋아요 정보 가져오기.
	public List<AnimalLike> findAnimalLikeByUserid(String user_id) {
		System.out.println("find AnimalLike By Userid Service");
		return animalLikeRepo.findAnimalLikeByUserid(user_id);
	}
	
	// 유저 아이디와 desertion_no를 통해 좋아요 정보 가져오기.
		public AnimalLike findAnimalLike(String user_id, String desertion_no) {
			System.out.println("find AnimalLike By (Userid,desertion_no) Service" + user_id + " " + desertion_no);
			AnimalLike animalLike = animalLikeRepo.findAnimalLike(user_id, desertion_no);
			if(animalLike!=null)
				System.out.println(animalLike.toString());
			return animalLike;
		}
	
	// 좋아요 삭제
	public void deleteAnimalLike(String user_id, String desertion_no) {
        System.out.println("Delete AnimalLike Service");
        animalLikeRepo.deleteAnimalLike(user_id, desertion_no);
    }
}
