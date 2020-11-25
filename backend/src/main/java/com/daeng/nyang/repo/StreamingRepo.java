package com.daeng.nyang.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.daeng.nyang.dto.Streaming;

public interface StreamingRepo extends JpaRepository<Streaming, Long>{
	
	@Query(value="insert into streaming(user_id, title, contents) values(:user_id, :title, :contents)", nativeQuery = true)
	void startStreaming(String user_id, String title, String contents);

	@Query(value="delete from streaming where user_id = :user_id", nativeQuery = true)
	void stopStreaming(String user_id);
}
