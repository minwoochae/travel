package com.travel.Dto;

import com.travel.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
	private String itemNm;
	
	private int count;
	
	private int orderPrice;
	
	private String imgUrl;
	
	public OrderItemDto(OrderItem orderItem, String imgUrl) {
		this.count = orderItem.getOrderCount();
		this.orderPrice = orderItem.getOrderPrice();
		this.imgUrl = imgUrl;
	}
}
