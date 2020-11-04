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
	
	private Animal animal;
	private boolean like;
	

}
