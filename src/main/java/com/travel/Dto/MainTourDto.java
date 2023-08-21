package com.travel.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainTourDto {

	private Long id;
	
	private String touristTitle;
	
	private String touristContent;
	
	@QueryProjection 
	public MainTourDto(Long id, String touristTitle, String touristContent) {
		this.id = id;
		this.touristTitle = touristTitle;
		this.touristContent = touristContent;
	}
	
}
