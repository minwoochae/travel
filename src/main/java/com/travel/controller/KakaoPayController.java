package com.travel.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.KakaoPayReadyDto;
import com.travel.Dto.OrderDto;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Orders;
import com.travel.entity.Pay;
import com.travel.service.KakaoPayService;
import com.travel.service.PayService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoPayController {
	private final KakaoPayService kakaoPayService;
	private final PayService payService;
	
	@GetMapping("/pay/ready")
	@ResponseBody
	public KakaoPayReadyDto kakaoPay(@ModelAttribute  OrderDto orderDto,  Model model, HttpSession session, Principal principal) {
		Map<String, Object> params = new HashMap<>();
		//params.put("orderItemId", orderDto.getOrderItemId());
	    params.put("itemName", orderDto.getItemName());
	    params.put("totalPrice", orderDto.getTotalPrice());
	    params.put("orderName", orderDto.getOrderName());
	    params.put("zipCode", orderDto.getZipCode());
	    params.put("orderAddress", orderDto.getOrderAddress());
	    params.put("phoneNumber", orderDto.getPhoneNumber());
	    params.put("cartItemid", orderDto.getOrderItemId());
	    System.out.println(params);
	    
		KakaoPayReadyDto res = kakaoPayService.kakaoPay(params);
		
		
	    // 주문 정보 생성 및 연결
	    Orders orders = new Orders();
	    orders.setOrderStatus(OrderStatus.CANCEL); // 예시: 주문 상태 설정
		
	    // tid 값을 세션에 저장
	    session.setAttribute("tid", res.getTid());
	    session.setAttribute("cartItem", orderDto.getOrderItemId());
	    // tid 값을 success 페이지로 전달하기 위해 모델에 추가
	    model.addAttribute("tid", res.getTid());
		return res;
	}
	
	@GetMapping("/pay/success")
	public String success(@RequestParam("pg_token")String pgToken,HttpSession session, Principal principal) {
			String tid = (String) session.getAttribute("tid");
			Long cartItemId = (Long) session.getAttribute("cartItem");
			
			System.out.println(cartItemId + "mnnnnnnnnnnnnn");
	        // 카카오 결재 요청하기
	        KakaoPayApproveDto kakaoPayApproveDto = kakaoPayService.kakaoPayApprove(tid, pgToken);
	        System.out.println(kakaoPayApproveDto);
	 
	        
//	        kakaoPayService.saveOrderAndPay(kakaoPayApproveDto, cartItemId, principal);

	        return "/main";
	}
	
	
	private String generateRandomPayNo() {
	    SecureRandom secureRandom = new SecureRandom();
	    byte[] randomBytes = new byte[16];
	    secureRandom.nextBytes(randomBytes);
	    return new String(randomBytes);
	}
	
	
}