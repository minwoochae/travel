package com.travel.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoSearchDto {

	private String searchDateType;
	private String searchBy;
	private String searchQuery = "";
}
