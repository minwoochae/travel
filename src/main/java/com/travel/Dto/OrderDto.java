package com.travel.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
	private Long[] orderItemIds;
    private String itemName;
    private int totalPrice;
    private String orderName;
    private String zipCode;
    private String orderAddress;
    private String phoneNumber;
}
