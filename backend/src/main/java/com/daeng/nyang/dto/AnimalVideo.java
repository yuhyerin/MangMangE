package com.daeng.nyang.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class AnimalVideo {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long uid;
	
	@Column
	private long desertion_no;
	@Column
	private String title;
	@Column
	private String filepath;
	@Column
	private String content;
	@Column
	private String writer;
	@CreationTimestamp
	private Date regtime;
	

}
