package com.daeng.nyang.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor @AllArgsConstructor
@ToString @Getter @Setter
public class Video {
	
	private long dersertion_no;
	private String title;
	private String content;
	

}
