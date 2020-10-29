package com.daeng.nyang.repo;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Animal;

@Repository
@Transactional
public interface AnimalRepo extends JpaRepository<Animal, String>{
	
	@Query(value="select * from animal", nativeQuery = true)
	List<Animal> findAnimalAll();
	
	// 100퍼센트 일치. 추천율 100프로.
	@Query(value="select * from animal where mbti=:mbti", nativeQuery = true)
	List<Animal> findAnimalByMbti(String mbti);
	
	@Query(value="select * from animal a where a.mbti like %:mbti%", nativeQuery = true)
	List<Animal>  findAnimalMbtiByKeyword(@Param("mbti") String mbti);
	
//	@Query(value="select * from animal a where (a.mbti like %:first%) and (a.mbti like %:second%) and (a.mbti like %:third%) and (a.mbti like %:fourth%)", nativeQuery = true)
//	List<Animal>  findAnimalMbtiByMbti(@Param("first")String first, @Param("second") String second, @Param("third") String third, @Param("fourth") String fourth);
	
	
	
	
}
