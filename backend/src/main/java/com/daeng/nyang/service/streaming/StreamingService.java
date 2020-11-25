package com.daeng.nyang.service.streaming;

public interface StreamingService {
	
	void startStreaming(String user_id, String title, String contents);

	void stopStreaming(String user_id);

}
