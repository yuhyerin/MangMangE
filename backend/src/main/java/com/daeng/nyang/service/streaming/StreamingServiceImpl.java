package com.daeng.nyang.service.streaming;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daeng.nyang.repo.StreamingRepo;

@Service
public class StreamingServiceImpl implements StreamingService{

	@Autowired
	private StreamingRepo streamingRepo;

	@Override
	public void startStreaming(String user_id, String title, String contents) {
		streamingRepo.startStreaming(user_id, title, contents);
	}

	@Override
	public void stopStreaming(String user_id) {
		streamingRepo.stopStreaming(user_id);
		
	}
	

}
