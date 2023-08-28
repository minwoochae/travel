package com.travel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.KakaoPayReadyDto;

import com.travel.service.KakaoPayService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@Log
public class KakaoPayController {
	private final KakaoPayService kakaoPayService;
	
	private final Logger logger = LoggerFactory.getLogger(KakaoPayController.class);
	@GetMapping("/pay/ready")
	public @ResponseBody KakaoPayReadyDto kakaoPay(@RequestParam String item_name,
	        @RequestParam String quantity,
	        @RequestParam String total_amount,
	        @RequestParam String tax_free_amount, Model model) {
		
		System.out.println("와라아아ㅏ아");
		Map<String, Object> params = new HashMap<>();
	    params.put("item_name", item_name);
	    params.put("quantity", quantity);
	    params.put("total_amount", total_amount);
	    params.put("tax_free_amount", tax_free_amount);
	    System.out.println(params);
		KakaoPayReadyDto res = kakaoPayService.kakaoPay(params);
		model.addAttribute("tid", res.getTid());
		System.out.println("tid" + res.getTid());	
		System.out.println("res: " + res);
		log.info(res.toString());
		return res;
	}
	
	@GetMapping("/pay/success")
	public String success(@RequestParam("pg_token")String pgToken, @RequestParam ("tid") String tid) {
		log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
		log.info("결재고유 번호: " + tid);
		
		// 카카오 결재 요청하기
		KakaoPayApproveDto kakaoPayApproveDto = kakaoPayService.kakaoPayApprove(tid, pgToken);
		return "/";
	}
}
