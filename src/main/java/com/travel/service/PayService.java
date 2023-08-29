package com.travel.service;

import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.PayDto;
import com.travel.Repository.CartRepository;
import com.travel.Repository.ItemImgRepository;
import com.travel.Repository.ItemRepository;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.PayRepository;
import com.travel.entity.Cart;
import com.travel.entity.Member;
import com.travel.entity.Pay;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PayService {
	  private final PayRepository payRepository;
	  private final CartRepository cartRepository;
	  private final MemberRepository memberRepository;

	  public Pay createPay(KakaoPayApproveDto kakaoPayApproveDto, Principal principal) {
		  
		  Pay pay = new Pay();
	        
	        // Cart와의 조인을 위해 CartId 설정
	       // pay.setCart(kakaoPayApproveDto);

	        // 난수 생성 메소드
	        pay.setPayNo(generateRandomPayNo());

	        // 이전 페이지에서 받은 price 값 설정
	        //pay.setPrice(price);

	        LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = now.format(formatter);
	        pay.setPayDate(formattedDate);

	        // 현재 로그인한 사용자의 이메일 가져오기
	        String email = principal.getName();
	        // 이메일을 이용하여 Member 정보 조회
	        Member member = memberRepository.findByEmail(email);

	        // Member 정보 설정
	        pay.setMember(member);

	        return payRepository.save(pay);
		  
		 
	  }
	  
	    private String generateRandomPayNo() {
	        SecureRandom secureRandom = new SecureRandom();
	        byte[] randomBytes = new byte[16];
	        secureRandom.nextBytes(randomBytes);
	        return new String(randomBytes);
	    }


}
