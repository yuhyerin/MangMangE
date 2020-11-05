package com.daeng.nyang.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalListFE;

@Repository
@Transactional
public interface AnimalRepo extends JpaRepository<Animal, String>{
	
	@Query(value="select * from animal order by desertion_no asc", nativeQuery = true)
	List<Animal> findAnimalAll();
	
	// 100퍼센트 일치. 추천율 100프로.
	@Query(value="select * from animal where mbti=:mbti order by desertion_no asc", nativeQuery = true)
	List<Animal> findAnimalByMbti(String mbti);
	
	@Query(value="select * from animal a where a.mbti like %:mbti% order by desertion_no asc", nativeQuery = true)
	List<Animal>  findAnimalMbtiByKeyword(@Param("mbti") String mbti);
	
//	@Query(value="select * from animal a where (a.mbti like %:first%) and (a.mbti like %:second%) and (a.mbti like %:third%) and (a.mbti like %:fourth%)", nativeQuery = true)
//	List<Animal>  findAnimalMbtiByMbti(@Param("first")String first, @Param("second") String second, @Param("third") String third, @Param("fourth") String fourth);
	
	@Query(value="select * from animal a where a.desertion_no=:desertion_no", nativeQuery = true)
	Animal findAnimalByDesertionNo(Long desertion_no);
	
}
