package com.daeng.nyang.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="user")
@Table(name="user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
	
	@Id
	private String user_id;
	@Column(nullable=false)
	private String user_email;
	@Column(nullable=false)
	private String user_password;
	private String user_name;

}
