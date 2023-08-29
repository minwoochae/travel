package com.travel.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.KakaoPayReadyDto;
import com.travel.Dto.OrderDto;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Member;
import com.travel.entity.Orders;
import com.travel.entity.Pay;
import com.travel.service.KakaoPayService;
import com.travel.service.MemberService;
import com.travel.service.PayService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoPayController {
	private final KakaoPayService kakaoPayService;
	private final PayService payService;
	private final MemberService memberService;
	
	@GetMapping("/pay/ready")
	@ResponseBody
	public KakaoPayReadyDto kakaoPay(@ModelAttribute OrderDto orderDto,  Model model, HttpSession session, Principal principal) {
		Map<String, Object> params = new HashMap<>();
		//params.put("orderItemId", orderDto.getOrderItemId());
	    params.put("itemName", orderDto.getItemName());
	    params.put("totalPrice", orderDto.getTotalPrice());
	    params.put("orderName", orderDto.getOrderName());
	   // params.put("zipCode", orderDto.getZipCode());
	   // params.put("orderAddress", orderDto.getOrderAddress());
	   // params.put("phoneNumber", orderDto.getPhoneNumber());
	    System.out.println(params);
	    
		KakaoPayReadyDto res = kakaoPayService.kakaoPay(params);
	    // tid 값을 세션에 저장
	    session.setAttribute("tid", res.getTid());

		
	    // 주문 정보 생성 및 연결
	    Orders orders = new Orders();
	    orders.setOrderStatus(OrderStatus.CANCEL); // 예시: 주문 상태 설정
		
	    // tid 값을 세션에 저장
	    session.setAttribute("tid", res.getTid());
	    //session.setAttribute("cartItem", orderDto.getOrderItemId());
	    // tid 값을 success 페이지로 전달하기 위해 모델에 추가
	    String item_name = orderDto.getItemName();
	    String total_price = orderDto.getTotalPrice();
	    System.out.println("힝" + item_name + total_price);
	    model.addAttribute("tid", res.getTid());
	    session.setAttribute("item_name", item_name);
	    session.setAttribute("total_price", total_price);
		return res;
	}
	
	@GetMapping("/pay/success")
	public String success(@RequestParam("pg_token")String pgToken,HttpSession session, Principal principal, Model model) {
			String tid = (String) session.getAttribute("tid");
			String itemName = (String) session.getAttribute("item_name");
			String totalPrice = (String) session.getAttribute("total_price");

			String email = principal.getName();
			Member member = memberService.findByEmail(email);
			
	        // 카카오 결재 요청하기
	        KakaoPayApproveDto kakaoPayApproveDto = kakaoPayService.kakaoPayApprove(tid, pgToken);
	        System.out.println(kakaoPayApproveDto);
	        
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        

		     // 현재 시간을 가져옵니다.
		     Date currentDate = new Date();
		     String currentDateString = dateFormat.format(currentDate); // 현재 시간을 문자열로 변환합니다.
	        
	        Pay pay = new Pay();
	        pay.setPrice(totalPrice);
	        pay.setPayNo(generateRandomPayNo());
	        pay.setPayDate(currentDateString);
	        pay.setMember(member);

	        kakaoPayService.savePay(pay);
	        // HTML에 데이터 전달
	        model.addAttribute("item_name", itemName);
	        model.addAttribute("total_price", totalPrice);

	        return "/item/paySuccess";
	}
	
	
	private String generateRandomPayNo() {
	    Random random = new Random();
	    int randomNumber = random.nextInt(1000000000); // 0에서 999999 사이의 난수 생성
	    return String.valueOf(randomNumber); // 숫자를 문자열로 변환하여 반환
	}
	
	
}