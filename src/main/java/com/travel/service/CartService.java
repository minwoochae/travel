package com.travel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.CartListDto;
import com.travel.Dto.ItemImgDto;
import com.travel.Repository.CartItemRepository;
import com.travel.Repository.CartRepository;
import com.travel.Repository.ItemImgRepository;
import com.travel.Repository.ItemRepository;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.OrderItemRepository;
import com.travel.constant.OrderStatus;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.entity.ItemImg;
import com.travel.entity.Member;
import com.travel.entity.OrderItem;
import com.travel.entity.Orders;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
	private final CartRepository cartRepository;
	private final ItemRepository itemRepository;
	private final MemberRepository memberRepository;
	private final ItemImgRepository itemImgRepository;
	private final CartItemRepository cartItemRepository;
	private final OrderItemRepository orderItemRepository;
	
	public Long addToCart(CartDto cartDto, Member member) {
	    System.out.println("오긴했는지?");
	    Cart cart = member.getCart();
	    System.out.println(cart);

	    if (cart == null) {
	        cart = Cart.createCart(member);
	        member.setCart(cart);
	    }

	    Item item = itemRepository.findById(cartDto.getItemId())
	            .orElseThrow(EntityNotFoundException::new);

	    // 아이템 정보를 사용하여 CartItem 생성
	    CartItem cartItem = new CartItem();
	    cartItem.setItem(item);
	    cartItem.setCount(cartDto.getCount());
	    cartItem.setCart(cart);
	    cartItem.setImgUrl(cartDto.getImgUrl());
	    
	    System.out.println(cartItem.getCount());
	    System.out.println(cartItem.getItem().getPrice());
	    System.out.println(cartItem.getCart());

	    cartItemRepository.save(cartItem);
	    cartRepository.save(cart); // Cart 저장

	    return cart.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<CartListDto> getCartList(String email, Pageable pageable) {
		List<Cart> carts = cartRepository.findCarts(email, pageable);
				
		Long totalCount = cartRepository.countCart(email);
		
		List<CartListDto> cartListDtos = new ArrayList<>();
		
		for (Cart cart : carts) {
			CartListDto cartListDto = new CartListDto(cart);
			List<CartItem> cartItems = cart.getCartItems();
			for(CartItem cartItem : cartItems) {
				CartItemDto cartItemDto = new CartItemDto(cartItem);
				cartListDto.addCartItemDto(cartItemDto);
			}
			cartListDtos.add(cartListDto);
		}
		
		return new PageImpl<CartListDto>(cartListDtos, pageable, totalCount);
	}
	
	@Transactional(readOnly = true)
	public boolean validateCart(Long cartId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		
		Member savedMember = cartItem.getCart().getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	
	public void deleteCart(Long cartId) {
		CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		
		cartItemRepository.delete(cartItem);
	}
	

	 
}
