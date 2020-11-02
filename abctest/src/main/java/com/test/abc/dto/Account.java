package com.test.abc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@Entity
@AllArgsConstructor @NoArgsConstructor
public class Account {
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long uid;
    @Id
	@Column(length=100)
	private String user_id;
	
	@Column(nullable=false, length=50)
	private String user_name;
	
	@Column(nullable=false, length=100)
	private String user_password;
	
	@Enumerated(EnumType.STRING)
	private Role role;

}
