package com.travel.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Order;
import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.KakaoPayApproveDto;
import com.travel.Dto.OrderDto;
import com.travel.Dto.OrderItemDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
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
	 
	 public void setOrderItem(CartItem cartItem, OrderItem orderItem) {
			
	        Item item = cartItem.getItem();
	        item.removeStock(cartItem.getCount());
		}
	 
}