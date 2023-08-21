package com.travel.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
	
	private Long itemId;
	
	private String member;
	
	private Long count;
}
