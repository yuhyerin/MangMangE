package com.test.abc.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString @Getter @Setter
@Builder
@Entity
@Table(name="Account")
@AllArgsConstructor @NoArgsConstructor
public class Account {
	
	@Id
	private Long uid;
	
	private String user_id;
	private String user_name;
	private String user_password;

}
