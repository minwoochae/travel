package com.travel.service;


import java.util.LinkedHashMap;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.KakaoPayReadyDto;
import com.travel.Repository.OrderItemRepository;
import com.travel.Repository.OrderRepository;
import com.travel.Repository.PayRepository;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.OrderItem;
import com.travel.entity.Orders;
import com.travel.entity.Pay;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
	private final OrderRepository orderRepository;
	private final PayRepository payRepository;
	private final OrderItemRepository orderItemRepository;

	//카카오페이 호출하기
	public KakaoPayReadyDto kakaoPay(Map<String, Object> params) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK d08ec3ab35a48fa351e5d8a58985ccdf");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		System.out.println(params.get("itemName")+ "ddddddddddddddd" );
		payParams.add("cid", "TC0ONETIME");
		payParams.add("partner_order_id", "partner_order_id");
		payParams.add("partner_user_id", "partner_user_id");
		payParams.add("item_name", params.get("itemName"));
		payParams.add("quantity", "1");
		payParams.add("total_amount", params.get("totalPrice"));
		payParams.add("tax_free_amount", "0");
		payParams.add("approval_url", "http://localhost/pay/success");
		payParams.add("cancel_url", "http://localhost/pay/cancel");
		payParams.add("fail_url", "http://localhost/pay/fail");
		
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(payParams, headers);
		
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
		KakaoPayReadyDto res = template.postForObject(url, request, KakaoPayReadyDto.class);
		
		return res;
	}
	
	//카카오페이
	public KakaoPayApproveDto kakaoPayApprove(String tid, String pgToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK d08ec3ab35a48fa351e5d8a58985ccdf");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		
		payParams.add("cid", "TC0ONETIME");
		payParams.add("tid", tid);
		payParams.add("partner_order_id", "partner_order_id");
		payParams.add("partner_user_id", "partner_user_id");
		payParams.add("pg_token", pgToken);
		
		
		HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);
		
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
		
		KakaoPayApproveDto res = template.postForObject(url, request, KakaoPayApproveDto.class);
		
		return res;
	}

	//결제정보 저장
    public void savePay(Pay pay) {
        payRepository.save(pay);
    }
    
    //주문정보 저장
    public void saveOrders(Orders orders) {
    	orderRepository.save(orders);
    }
    
    //주문상품 정보 저장
    public void saveOrderItem(OrderItem orderItem) {
    	orderItemRepository.save(orderItem);
    }
}