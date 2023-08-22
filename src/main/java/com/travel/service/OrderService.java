package com.travel.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.OrderDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
import com.travel.Repository.MemberRepository;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final CartItemRepository cartItemRepository;
	
	public List<CartItem> findByIdIn(){
//	    List<CartItem> selectedItems = cartItemRepository.findByIdIn(Arrays.asList(selectedProductIds));	        // 선택된 상품들의 정보를 모델에 추가하거나 필요한 작업 수행

//	    return selectedItems;
	    return  null;
	}
}
