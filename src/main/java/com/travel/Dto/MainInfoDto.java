package com.travel.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainInfoDto {

	private Long id;
	
	private String infoTitle;
	
	private String infoContent;
	
	@QueryProjection 
	public MainInfoDto(Long id, String infoTitle, String infoContent) {
		this.id = id;
		this.infoTitle = infoTitle;
		this.infoContent = infoContent;
	}
	
}
