package com.daeng.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalListFE {
	
	private Long desertion_no;
	private String kind_c;
	private String color_cd;
	private int age;
	private float weight;
	private String popfile;
	private String process_state;
	private Character sex_cd;
	private Character neuter_yn;
	private String special_mark;
	private String charge_nm;
	private String officetel;
	private String mbti;

	private String[] personality;
	private boolean like;
	

}
