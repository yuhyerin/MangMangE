package com.daeng.nyang.service.animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalLike;
import com.daeng.nyang.dto.AnimalListFE;
import com.daeng.nyang.dto.Apply;
import com.daeng.nyang.dto.Ptag;
import com.daeng.nyang.repo.AnimalLikeRepo;
import com.daeng.nyang.repo.AnimalRepo;
import com.daeng.nyang.repo.ApplyRepo;
import com.daeng.nyang.repo.PtagRepo;

import edu.emory.mathcs.backport.java.util.Arrays;

@Service
public class AnimalService {

	@Autowired
	private AnimalRepo animalRepo;

	@Autowired
	private PtagRepo ptagRepo;

	@Autowired
	private AnimalLikeRepo animalLikeRepo;
	
	@Autowired
	private ApplyRepo applyRepo;

	// (비회원) 모든동물조회
	public List<AnimalListFE> allAnimalRead() {
		// 1. 동물리스트
		List<Animal> animalList = animalRepo.findAll();
		List<Ptag> personalityList = ptagRepo.findAll();
		List<AnimalListFE> result = new ArrayList<>();

		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			// 2. 각 동물의 personality
			List<String> personalList = new ArrayList<>();
			for (int j = 0; j < personalityList.size(); j++) {
				Ptag tag = personalityList.get(j);
				if ((long) tag.getDesertion_no() == (long) animal.getDesertion_no())
					personalList.add(tag.getTagname());
			}
			String[] personality = new String[personalList.size()];
			for (int z = 0; z < personality.length; z++) {
				personality[z] = personalList.get(z);
			}

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

	// (회원) 모든 동물 조회
	public List<AnimalListFE> allAnimalRead(String user_id) {
		// 1. 동물 리스트
		List<Animal> animalList = animalRepo.findAnimalAll();
		List<Ptag> personalityList = ptagRepo.findAll();
		Long[] likeList = (Long[]) animalLikeRepo.findDesertionNoByUserId(user_id);
		List<AnimalListFE> result = new ArrayList<>();
		for (int i = 0; i < animalList.size(); i++) {
			Animal animal = animalList.get(i);
			long aniDN = (long) animal.getDesertion_no();
			List<String> personalList = new ArrayList<String>();
			// 2. 각 동물의 personality
			for (int j = 0; j < personalityList.size(); j++) {
				Ptag tag = personalityList.get(j);
				long tagDN = (long)tag.getDesertion_no();
				if (tagDN == aniDN)
					personalList.add(tag.getTagname());
			}
			
			// 3. 좋아요 여부
			boolean like = false;
			for (int k = 0; k < likeList.length; k++) {
				if ((long) animal.getDesertion_no() == likeList[k]) {
					like = true;
					break;
				}
			}
			
			String[] personality = new String[personalList.size()];
			for (int z = 0; z < personality.length; z++)
				personality[z] = personalList.get(z);
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
	
	// (회원) 입양신청정보 조회
	public boolean findApply(long desertion_no, String user_id) {
		Optional<Apply> app = applyRepo.findApplyByDesertionNoAndUserId(desertion_no, user_id);
		return app.isPresent();
	}
	
	// (회원) 즐겨찾는 동물 조회
	public List<AnimalListFE> animalLikeList(String user_id) {
		List<AnimalListFE> result = new ArrayList<>();
		List<Animal> animals = animalRepo.findAll();
		List<Ptag> personalities = ptagRepo.findAll();

		// 1. animalLike 테이블에서 desertion_no 리스트 가져오기
		Long[] desertion_no = animalLikeRepo.findDesertionNoByUserId(user_id);
		// 2. animal 테이블에서 desertion_no로 animal 정보 가져오기
		for (int i = 0; i < desertion_no.length; i++) {
			List<String> personalList = new ArrayList<>();
			for (int j=0; j < personalities.size(); j++) {
				Ptag temp = personalities.get(j);
				if ((long) temp.getDesertion_no() == (long) desertion_no[i])
					personalList.add(temp.getTagname());
			}
			String[] personality = new String[personalList.size()];
			for (int z = 0; z < personality.length; z++) {
				personality[z] = personalList.get(z);
			}
			Animal animal = null;
			for (int k=0; k < animals.size(); k++) {
				animal = animals.get(k);
				if((long) animal.getDesertion_no()== (long) desertion_no[i]) {
					break;
				}
			}
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
		AnimalLike result = animalLikeRepo.save(animalLike);
		return result;
	}

	// 유저 아이디를 통해 좋아요 정보 가져오기.
	public List<AnimalLike> findAnimalLikeByUserid(String user_id) {
		return animalLikeRepo.findAnimalLikeByUserid(user_id);
	}

	// 유저 아이디와 desertion_no를 통해 좋아요 정보 가져오기.
	public AnimalLike findAnimalLike(String user_id, Long desertion_no) {
		AnimalLike animalLike = animalLikeRepo.findAnimalLike(user_id, desertion_no);
		return animalLike;
	}

	// 좋아요 삭제
	public void deleteAnimalLike(String user_id, Long desertion_no) {
		animalLikeRepo.deleteAnimalLike(user_id, desertion_no);
	}

	public AnimalListFE animalDetail(Long desertion_no) {
		Animal animal = animalRepo.findAnimalByDesertionNo(desertion_no);
		String[] personality = animalPersonality(desertion_no);
		AnimalListFE result = AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
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
				like(false).build();
		return result;
	}

	public AnimalListFE animalDetail(String user_id, Long desertion_no) {
		Animal animal = animalRepo.findAnimalByDesertionNo(desertion_no);
		String[] personality = animalPersonality(desertion_no);
		boolean like = animalLikeRepo.findAnimalLikeByUserIdAndDesertionNo(user_id, desertion_no).isPresent();
		AnimalListFE result = AnimalListFE.builder().desertion_no(animal.getDesertion_no()). // 유기번호
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
				like(like).build();
		return result;
	}

	public String[] animalPersonality(Long desertion_no) {
		String[] personality = ptagRepo.findTagNameByDesertionNo(desertion_no);
		return personality;
	}

}
