package com.daeng.nyang.service.animal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalLike;
import com.daeng.nyang.dto.AnimalListFE;
import com.daeng.nyang.repo.AnimalLikeRepo;
import com.daeng.nyang.repo.AnimalRepo;
import com.daeng.nyang.repo.PtagRepo;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepo animalRepo;

	@Autowired
	private PtagRepo ptagRepo;

	@Autowired
	private AnimalLikeRepo animalLikeRepo;

	public List<AnimalListFE> allAnimalRead() {
		// 1. 동물리스트
		List<Animal> animalList = animalRepo.findAnimalAll();
		List<AnimalListFE> result = new ArrayList<>();
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			// 2. 각 동물의 personality
			String[] personality = ptagRepo.findTagNameByDesertionNo(animal.getDesertion_no());

			result.add(AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
					kind_c(animal.getKind_c()). // 하위 품종
					color_cd(animal.getColor_cd()). // 털색
					age(animal.getAge()). // 출생년도(int(4))
					weight(animal.getWeight()). // 체중
					popfile(animal.getPopfile()). // 이미지
					process_state(animal.getProcess_state()). // 입양상태
					sex_cd(animal.getSex_cd()). // 성별
					neuter_yn(animal.getNeuter_yn()). // 중성화여부
					special_mark(animal.getSpecial_mark()). // 특징
					charge_nm(animal.getCharge_nm()). // 담당자 이름
					officetel(animal.getOfficetel()). // 담당자 연락처
					mbti(animal.getMbti()). // 동물 mbti
					personality(personality). // 동물 성격
					like(false).build() // 좋아요 여부
			);
		}
		return result;
	}

	public List<AnimalListFE> allAnimalRead(String user_id) {
		// 1. 동물 리스트
		List<Animal> animalList = animalRepo.findAnimalAll();
		List<AnimalListFE> result = new ArrayList<>();
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			// 2. 각 동물의 personality
			String[] personality = ptagRepo.findTagNameByDesertionNo(animal.getDesertion_no());
			// 3. 좋아요 여부
			boolean like = animalLikeRepo.findAnimalLikeByUserIdAndDesertionNo(user_id, animal.getDesertion_no())
					.isPresent();

			result.add(AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
					kind_c(animal.getKind_c()). // 하위 품종
					color_cd(animal.getColor_cd()). // 털색
					age(animal.getAge()). // 출생년도(int(4))
					weight(animal.getWeight()). // 체중
					popfile(animal.getPopfile()). // 이미지
					process_state(animal.getProcess_state()). // 입양상태
					sex_cd(animal.getSex_cd()). // 성별
					neuter_yn(animal.getNeuter_yn()). // 중성화여부
					special_mark(animal.getSpecial_mark()). // 특징
					charge_nm(animal.getCharge_nm()). // 담당자 이름
					officetel(animal.getOfficetel()). // 담당자 연락처
					mbti(animal.getMbti()). // 동물 mbti
					personality(personality). // 동물 성격
					like(like).build() // 좋아요 여부
			);
		}
		return result;
	}

	// 100퍼센트 일치. 추천율 100프로.
	public List<AnimalListFE> findAnimalByMbti(String user_id, String mbti) {
		// 1. mbti 매칭동물리스트
		List<Animal> list = animalRepo.findAnimalByMbti(mbti);
		List<AnimalListFE> result = new ArrayList<AnimalListFE>();
		for (int i = 0; i < list.size(); i++) {
			Animal animal = list.get(i);
			// 2. 각 동물별 성격
			String[] personality = ptagRepo.findTagNameByDesertionNo(animal.getDesertion_no());
			// 3. 좋아요 여부
			boolean like = animalLikeRepo.findAnimalLikeByUserIdAndDesertionNo(user_id, animal.getDesertion_no())
					.isPresent();
			result.add(AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
					kind_c(animal.getKind_c()). // 하위 품종
					color_cd(animal.getColor_cd()). // 털색
					age(animal.getAge()). // 출생년도(int(4))
					weight(animal.getWeight()). // 체중
					popfile(animal.getPopfile()). // 이미지
					process_state(animal.getProcess_state()). // 입양상태
					sex_cd(animal.getSex_cd()). // 성별
					neuter_yn(animal.getNeuter_yn()). // 중성화여부
					special_mark(animal.getSpecial_mark()). // 특징
					charge_nm(animal.getCharge_nm()). // 담당자 이름
					officetel(animal.getOfficetel()). // 담당자 연락처
					mbti(animal.getMbti()). // 동물 mbti
					personality(personality). // 동물 성격
					like(like).build());
		}

		return result;
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
		System.out.println("RESULT : " + result.toString());
		return result;
	}

	// 유저 아이디를 통해 좋아요 정보 가져오기.
	public List<AnimalLike> findAnimalLikeByUserid(String user_id) {
		System.out.println("find AnimalLike By Userid Service");
		return animalLikeRepo.findAnimalLikeByUserid(user_id);
	}

	// 유저 아이디와 desertion_no를 통해 좋아요 정보 가져오기.
	public AnimalLike findAnimalLike(String user_id, Long desertion_no) {
		AnimalLike animalLike = animalLikeRepo.findAnimalLike(user_id, desertion_no);
		if (animalLike != null)
			System.out.println(animalLike.toString());
		return animalLike;
	}

	// 좋아요 삭제
	public void deleteAnimalLike(String user_id, Long desertion_no) {
		System.out.println("Delete AnimalLike Service");
		animalLikeRepo.deleteAnimalLike(user_id, desertion_no);
	}

	public Animal animalDetail(Long desertion_no) {
		System.out.println("SERVICE START");
		Animal animal = animalRepo.findAnimalByDesertionNo(desertion_no);
		System.out.println(animal.toString());
		/**
		 * 제목 종 / 품종 성별(중성화) 나이 몸무게 털색 성격
		 */
		return animal;
	}

	public String[] animalPersonality(Long desertion_no) {
		System.out.println("SERVICE START");
		String[] personality = ptagRepo.findTagNameByDesertionNo(desertion_no);
		System.out.println(personality.toString());
		System.out.println("SERVICE END");
		return personality;
	}

	public List<AnimalListFE> animalLikeList(String user_id) {
		System.out.println("SERVICE START : 좋아요 리스트");
		List<AnimalListFE> result = new ArrayList<>();

		// 1. animalLike 테이블에서 desertion_no 리스트 가져오기
		List<Long> desertion_no = animalLikeRepo.findDesertionNoByUserId(user_id);
		System.out.println(desertion_no.toString());
		// 2. animal 테이블에서 desertion_no로 animal 정보 가져오기
		for (int i = 0; i < desertion_no.size(); i++) {
			Long no = desertion_no.get(i);
			Animal animal = animalRepo.findAnimalByDesertionNo(no);
			String[] personality = animalPersonality(no);
			result.add(AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
					kind_c(animal.getKind_c()). // 하위 품종
					color_cd(animal.getColor_cd()). // 털색
					age(animal.getAge()). // 출생년도(int(4))
					weight(animal.getWeight()). // 체중
					popfile(animal.getPopfile()). // 이미지
					process_state(animal.getProcess_state()). // 입양상태
					sex_cd(animal.getSex_cd()). // 성별
					neuter_yn(animal.getNeuter_yn()). // 중성화여부
					special_mark(animal.getSpecial_mark()). // 특징
					charge_nm(animal.getCharge_nm()). // 담당자 이름
					officetel(animal.getOfficetel()). // 담당자 연락처
					mbti(animal.getMbti()). // 동물 mbti
					personality(personality). // 동물 성격
					like(true).build());
		}
		System.out.println(result.toString());
		System.out.println("SERVICE END : 좋아요 리스트");
		return result;
	}
}
