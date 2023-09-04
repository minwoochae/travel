package com.travel.Dto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.travel.constant.OrderStatus;
import com.travel.entity.Orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistDto {
	private Long orderId;
	
	private String orderDate;
	
	private OrderStatus orderStatus;
	
	private String payNo;
	
	private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	
	public void addOrderItemDto(OrderItemDto orderItemDto) {
		this.orderItemDtoList.add(orderItemDto);
	}
	
	public OrderHistDto(Orders orders) {
		
		this.payNo = orders.getPay().getPayNo();
		this.orderId = orders.getId();
		this.orderDate = orders.getPay().getPayDate()
				.formatted(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm"));
		System.out.println(orders.getOrderStatus());
		this.orderStatus = orders.getOrderStatus();
	}
	
}
