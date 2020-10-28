package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Animal;

@Repository
public interface AnimalRepo extends JpaRepository<Animal, String>{
	
	@Query(value="insert into animal(desertionNo, filename, happenDt, happenPlace,"
			+ "kindCd, colorCd, age, weight,"
			+ "noticeNo, noticeSdt, noticeEdt, popfile, processState,"
			+ "sexCd, neuterYn, specialMark,"
			+ "careNm, careTel, careAddr, orgNm, chargeNm, officetel, mbti) "
			+ "values(:desertionNo , :filename, :happenDt, :happenPlace,"
			+ ":kindCd, :colorCd, :age, :weight,"
			+ ":noticeNo, :noticeSdt, :noticeEdt, :popfile, :processState,"
			+ ":sexCd, :neuterYn, :specialMark,"
			+ ":careNm, :careTel, :careAddr, :orgNm, :chargeNm, :officetel, :mbti )", nativeQuery=true)
	void insertAnimalFromApi(String desertionNo, String filename, String happenDt, String happenPlace, 
			String kindCd, String colorCd, String age, double weight,
			String noticeNo, String noticeSdt, String noticeEdt, String popfile, String processState,
			String sexCd, String neuterYn, String specialMark,
			String careNm, String careTel, String careAddr, String orgNm, String chargeNm, String officetel, String mbti);
}
