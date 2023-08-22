package com.travel.Dto;

import com.travel.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	private String itemNm;
	
	private Long count;
		
	private String imgUrl;
	
	private int price;
	
	
	
	public CartItemDto(CartItem cartItem, String imgUrl) {
		this.count = cartItem.getCount();
		this.imgUrl = imgUrl;
		this.price = cartItem.getItem().getPrice();
		this.itemNm = cartItem.getItem().getItemNm();
	}
}
