package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import com.travel.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartListDto {
	private Long cartId;
		
	private List<CartItemDto> cartItemDtoList = new ArrayList<>();
	
	public CartListDto(Cart cart) {
		this.cartId = cart.getId();
	}
	
	public void addCartItemDto(CartItemDto cartItemDto) {
		this.cartItemDtoList.add(cartItemDto);

	}
}
