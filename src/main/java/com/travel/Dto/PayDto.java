package com.travel.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayDto {
	
	private Long cartId;
	
	private int price;
	
	private String payNo;
	
	private String payDate;
}
