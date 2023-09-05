package com.travel.service;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.OrderDto;
import com.travel.Dto.OrderHistDto;
import com.travel.Dto.OrderItemDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
import com.travel.Repository.ItemImgRepository;
import com.travel.Repository.ItemRepository;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.OrderItemRepository;
import com.travel.Repository.OrderRepository;
import com.travel.Repository.PayRepository;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.entity.ItemImg;
import com.travel.entity.Member;
import com.travel.entity.OrderItem;
import com.travel.entity.Orders;
import com.travel.entity.Pay;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
	private final CartItemRepository cartItemRepository;
	private final OrderRepository orderRepository;
	private final PayRepository payRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final MemberRepository memberRepository;
    
    
    
    
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


	 public CartItem getCartItemById(Long itemId) {
		 
	        return cartItemRepository.findById(itemId).orElse(null); // itemId에 해당하는 카트 항목 조회
	    }
	 
	 
	 @Transactional(readOnly = true)
	 public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
	     List<Orders> orders = orderRepository.findOrdersByEmail(email, pageable);
	     Long totalCount = orderRepository.countOrders(email);

	     List<OrderHistDto> orderHistDtos = new ArrayList<>();

	     for (Orders order : orders) {
	         OrderHistDto orderHistDto = new OrderHistDto(order);

	         // OrderItem 리스트 가져오기
	         List<OrderItem> orderItems = order.getOrderItems();
	         // OrderItemDto 리스트 생성
	         List<OrderItemDto> orderItemDtoList = new ArrayList<>();
	         for (OrderItem orderItem : orderItems) {

	             // ItemImg 가져오기
	             ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");

	             // OrderItemDto 생성 및 값 설정
	             OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());

	             // OrderItemDto를 리스트에 추가
	             orderItemDtoList.add(orderItemDto);
	         }

	         // OrderHistDto에 OrderItemDto 리스트 설정
	         orderHistDto.setOrderItemDtoList(orderItemDtoList);

	         // OrderHistDto를 결과 리스트에 추가
	         orderHistDtos.add(orderHistDto);
	     }

	     return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
	 }
	 
	 public void setOrderItem(CartItem cartItem, OrderItem orderItem) {
			orderItem.setItem(cartItem.getItem());
	        Item item = cartItem.getItem();
	        item.removeStock(cartItem.getCount());
		}

	 
}