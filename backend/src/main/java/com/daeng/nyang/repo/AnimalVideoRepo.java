package com.daeng.nyang.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daeng.nyang.dto.Animal;
import com.daeng.nyang.dto.AnimalVideo;

public interface AnimalVideoRepo extends JpaRepository<AnimalVideo, String>{
	
	@Query(value="select * from animal_video av where av.desertion_no=:desertion_no and av.title=:title", nativeQuery=true)
	public Optional<AnimalVideo> findByDesertionNoAndTitle(Long desertion_no, String title);

	@Query(value="select * from animal_video av where av.uid=:uid", nativeQuery=true)
	public AnimalVideo findByUid(Long uid);
	
	@Query(value="select * from animal_video av where av.filepath=:filepath", nativeQuery=true)
	public Optional<AnimalVideo> findByFilepath(String filepath);

	@Query(value="select * from animal_video order by uid asc", nativeQuery=true)
	List<AnimalVideo> findAnimalVideoAll();
	
	@Query(value="select * from animal_video where desertion_no=:desertion_no order by uid desc", nativeQuery = true)
	public List<AnimalVideo> findByDesertionNo(Long desertion_no);
}
