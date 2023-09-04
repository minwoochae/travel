package com.travel.Dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainAskResponseDto {

	private Long id;
	
	private String askResponseTitle;
	
	private String askResponseContent;
	
	@QueryProjection 
	public MainAskResponseDto(Long id, String askResponseTitle, String askResponseContent) {
		this.id = id;
		this.askResponseContent = askResponseTitle;
		this.askResponseContent = askResponseContent;
	}
}
