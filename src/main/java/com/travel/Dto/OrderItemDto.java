package com.travel.Dto;

import com.travel.entity.OrderItem;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
	
	private Long itemId;
	
	private String itemNm;
	
	@Min(value = 1, message = "최소 주문수량은 1개입니다.")
	private int count;
	
	private int orderPrice;
	
	private String imgUrl;
	
	public OrderItemDto(OrderItem orderItem, String imgUrl) {
		this.count = orderItem.getOrderCount();
		this.orderPrice = orderItem.getOrderPrice();
		this.itemNm = orderItem.getItem().getItemNm();
		this.imgUrl = imgUrl;
	}
}
