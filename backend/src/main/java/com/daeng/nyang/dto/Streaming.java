package com.daeng.nyang.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Streaming {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
	
	@Column(nullable=false, unique=true, length=100)
	private String user_id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
}
