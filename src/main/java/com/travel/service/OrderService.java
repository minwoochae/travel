package com.travel.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.OrderDto;
import com.travel.Dto.OrderItemDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.OrderRepository;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.ItemImg;
import com.travel.entity.OrderItem;
import com.travel.entity.Orders;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final CartItemRepository cartItemRepository;

	
	public List<CartItemDto> findItemsByIds(Long[] itemIds) {
        List<CartItemDto> items = new ArrayList<>();

        List<Long> itemIdIterable = Arrays.asList(itemIds);
        
        List<CartItem> cartItems = cartItemRepository.findByIdIn(itemIdIterable);
        
        for (CartItem cartItem : cartItems) {
            CartItemDto itemDto = new CartItemDto(cartItem);
            items.add(itemDto);
        }
        
        return items;
    }

	
		

	    }
	

