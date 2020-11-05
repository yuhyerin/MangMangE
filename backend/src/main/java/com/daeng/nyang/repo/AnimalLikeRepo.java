package com.daeng.nyang.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daeng.nyang.dto.AnimalLike;

@Repository
public interface AnimalLikeRepo extends JpaRepository<AnimalLike, Integer> {
	@Query(value="select * from animal_like where user_id=:user_id", nativeQuery = true)
	List<AnimalLike> findAnimalLikeByUserid(String user_id);
	
	@Query(value="select * from animal_like where user_id=:user_id and desertion_no=:desertion_no", nativeQuery = true)
	AnimalLike findAnimalLike(String user_id, String desertion_no);
	
	@Query(value="delete from animal_like where user_id=:user_id and desertion_no=:desertion_no", nativeQuery = true)
	void deleteAnimalLike(String user_id, String desertion_no);
}