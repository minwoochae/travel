package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.CartDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
import com.travel.Repository.ItemRepository;
import com.travel.Repository.MemberRepository;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.entity.Member;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	
	public Long cart(CartDto cartDto, String email) {
		
		Item item = itemRepository.findById(cartDto.getItemId())
				.orElseThrow(EntityNotFoundException::new);
		System.out.println(item.toString());
		
		Member member = memberRepository.findByEmail(email);
		
		System.out.println(member.toString());
		
		List<CartItem> cartItemList = new ArrayList<>();
		
		
		CartItem cartItem = CartItem.addCartItem(item, cartDto);
		System.out.println(cartItem);
		cartItemList.add(cartItem);
		System.out.println(cartItemList);
		
		Cart cart = Cart.createCart(member, cartItemList);
		cartRepository.save(cart);
		
		return cart.getId();
	}
	
}
