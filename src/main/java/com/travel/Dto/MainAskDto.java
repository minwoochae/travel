package com.travel.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainAskDto {

	private Long id;
	
	private String askTitle;
	
	private String askContent;
	
	@QueryProjection 
	public MainAskDto(Long id, String askTitle, String askContent) {
		this.id = id;
		this.askTitle = askTitle;
		this.askContent = askContent;
	}
}
