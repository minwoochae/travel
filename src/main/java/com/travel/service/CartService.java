package com.travel.service;

import java.util.ArrayList;
import java.util.List;

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
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.entity.ItemImg;
import com.travel.entity.Member;

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
	
	public Long cart(CartDto cartDto, String email) {
		
		Item item = itemRepository.findById(cartDto.getItemId())
				.orElseThrow(EntityNotFoundException::new);
		
		Member member = memberRepository.findByEmail(email);
		
		
		List<CartItem> cartItemList = new ArrayList<>();
		
		
		CartItem cartItem = CartItem.addCartItem(item,  cartDto);
		cartItemList.add(cartItem);
		Cart cart = Cart.createCart(member, cartItemList);
		
		cartRepository.save(cart);
		
		return cart.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<CartListDto> getCartList(String email, Pageable pageable) {
		List<Cart> carts = cartRepository.findCarts(email, pageable);
				
		Long totalCount = cartRepository.countCart(email);
		
		List<CartListDto> cartListDtos = new ArrayList<>();
		
		for (Cart cart : carts) {
			CartListDto cartListDto = new CartListDto(cart);
			List<CartItem> cartItems = cart.getCartitems();
			for(CartItem cartItem : cartItems) {
				ItemImg itemImg = itemImgRepository
						.findByItemIdAndRepimgYn(cartItem.getItem().getId(), "Y");
				CartItemDto cartItemDto = new CartItemDto(cartItem, itemImg.getImgUrl());
				cartListDto.addCartItemDto(cartItemDto);
			}
			cartListDtos.add(cartListDto);
		}
		
		return new PageImpl<CartListDto>(cartListDtos, pageable, totalCount);
	}
	
	@Transactional(readOnly = true)
	public boolean validateCart(Long cartId, String email) {
		Member curMember = memberRepository.findByEmail(email);
		Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		
		Member savedMember = cart.getMember();
		
		if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
			return false;
		}
		return true;
	}
	
	
	public void deleteCart(Long cartId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		
		cartRepository.delete(cart);
	}
}
