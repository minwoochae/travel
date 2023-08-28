package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.entity.CartItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
	private Long id;
	
	private String itemNm;
	
	private Long count;
		
	private String imgUrl;
	
	private int price;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public CartItemDto(CartItem cartItem) {
		this.id = cartItem.getId();
		this.count = cartItem.getCount();
		this.imgUrl = cartItem.getImgUrl();
		this.price = cartItem.getItem().getPrice();
		this.itemNm = cartItem.getItem().getItemNm();
	}
	
	public static CartItemDto of(CartItem cartItem) {
		return modelMapper.map(cartItem, CartItemDto.class);
	}
}
