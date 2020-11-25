package com.daeng.nyang.service.streaming;

import org.springframework.beans.factory.annotation.Autowired;

import com.daeng.nyang.repo.StreamingRepo;

public class StreamingServiceImpl implements StreamingService{

	@Autowired
	private StreamingRepo streamingRepo;
	
	@Override
	public void startStreaming() {
		
	}

}
