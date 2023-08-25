package com.travel.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskSearchDto {

	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
}
