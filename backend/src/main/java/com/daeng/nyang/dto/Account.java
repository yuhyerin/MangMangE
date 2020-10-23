package com.daeng.nyang.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable=false, unique=true, length=100)
	private String user_id;
	
	@Column(nullable=false)
	private String user_email;
	
	@Column(nullable=false)
	private String user_password;
	
	@Column(nullable=false, length=50)
	private String user_name;
	
	@CreationTimestamp
    private Date regdate;

    @UpdateTimestamp
    private Date updatedate;

    private String role;

}
