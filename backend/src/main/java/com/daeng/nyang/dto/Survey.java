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

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Survey {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 얘가 auto_increment
    private Long no;
	
	
	@Column
	private String user_id;
	
	@Column
	private String mbti; // user mbti

	@Column
	private String answer; // dog mbti

}