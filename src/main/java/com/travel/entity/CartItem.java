package com.travel.entity;

import java.util.ArrayList;
import java.util.List;

import com.travel.Dto.CartDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name="cart_item") 
@Getter
@Setter
public class CartItem {
	
	@Id
	@Column(name="cart_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name="count")
	private int count;
	
	@Column(name = "img_url")
	private String imgUrl;
	
	@ManyToOne
	@JoinColumn(name = "cart_id")
	private Cart cart;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private Item item;
	
	/*
	 * @OneToMany(mappedBy = "cartitem", cascade = CascadeType.ALL, orphanRemoval =
	 * true) private List<OrderItem> orderItems = new ArrayList<>();
	 */
	
	public static CartItem addCartItem(Item item ,  CartDto cartDto) {
	    CartItem cartItem = new CartItem();
	    cartItem.setImgUrl(cartDto.getImgUrl());
	    cartItem.setItem(item);
	    cartItem.setCount(cartDto.getCount());
	    return cartItem;
	}
	

}
