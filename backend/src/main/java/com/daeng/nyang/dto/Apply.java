package com.daeng.nyang.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Apply {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long uid;

	@Column
	private long Ani_num;	// FK
//	@ManyToOne(optional=false) @JoinColumn(name="user_id", insertable=false, updatable=false)
//	private Account account;	// FK
	private String user_id;
	private String user_name;
	private String user_phone;
	private String title;
	private String content;
	
	@Column @CreationTimestamp
	private Date createDateTime;

}
