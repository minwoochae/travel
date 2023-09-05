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
	
	//카트에 상품 추가하기
	public Long addToCart(CartDto cartDto, Member member) {
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
	
	//카트 상품리스트 불러오기
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
	
	//카트에 상품 몇개 들었는지 계산하기
	public long getCartItemCount(String memberName) {
	    Member member = memberRepository.findByEmail(memberName); // 사용자 엔티티 가져오기
	    if (member != null) {
	        Cart cart = member.getCart(); // 사용자의 장바구니 가져오기
	        if (cart != null) {
	            List<CartItem> cartItems = cart.getCartItems(); // 장바구니의 cartItem 목록 가져오기
	            System.out.println(cartItems.size() +"먳개고");
	            return cartItems.size(); // cartItem 개수 반환
	        }
	    }
	    return 0; // 장바구니가 없거나 cartItem이 없는 경우 0 반환
	}
	
	
	//카트 본인확인하기
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
	
	//카트 삭제하기
	public void deleteCart(Long cartId) {
		CartItem cartItem = cartItemRepository.findById(cartId).orElseThrow(EntityNotFoundException::new);
		
		cartItemRepository.delete(cartItem);
	}
	
	
	

	 
}
