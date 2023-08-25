package com.travel.service;


import java.util.List;
import java.util.Map;

import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.KakaoPayReadyDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
	
	public KakaoPayReadyDto kakaoPay(Map<String, Object> params) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 826f9676e26b9186cb6767cddf17d62b");
		
		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		
		payParams.add("cid", "TC0ONETIME");
		payParams.add("partner_order_id", "KAO20230318001");
		payParams.add("partner_user_id", "kakaopayTest");
		payParams.add("item_name", params.get("item_name"));
		payParams.add("quantity", params.get("quantity"));
		payParams.add("total_amount", params.get("total_amount"));
		payParams.add("tax_free_amount", params.get("tax_free_amount"));
		payParams.add("approval_url", "http://localhost/pay/success");
		payParams.add("fail_url", "http://localhost/pay/fail");
		payParams.add("cancel_url", "http://localhost/pay/cancel");
		
		HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);
		
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
		
		KakaoPayReadyDto res = template.postForObject(url, request, KakaoPayReadyDto.class);
		System.out.println(res + "55555555555555");
		
		return res;
	}
	
	
	public KakaoPayApproveDto kakaoPayApprove(String tid, String pgToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "KakaoAK 826f9676e26b9186cb6767cddf17d62b");
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, Object> payParams = new LinkedMultiValueMap<String, Object>();
		System.out.println(tid + "dddddddddddddddd");
		
		payParams.add("cid", "TC0ONETIME");
		payParams.add("tid", tid);
		payParams.add("partner_order_id", "KAO20230318001");
		payParams.add("partner_user_id", "kakaopayTest");
		payParams.add("pg_token", pgToken);
		
		HttpEntity<Map> request = new HttpEntity<Map>(payParams, headers);
		
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
		
		KakaoPayApproveDto res = template.postForObject(url, request, KakaoPayApproveDto.class);
		
		return res;
	}
}
