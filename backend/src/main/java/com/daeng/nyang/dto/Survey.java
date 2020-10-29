package com.daeng.nyang.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
	
	@Id
	@Column
	private String user_id;

	private String token;
	
	@Column
	private String mbti; // user mbti

	@Column
	private String answer; // dog mbti

}