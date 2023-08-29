package com.travel.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayApproveDto {
	private Long cartId;
	private String aid;
	private String tid;
	private String partner_order_is;
	private String payment_method_type;
	
}
