package com.travel.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name="cart") 
@Getter
@Setter
@ToString
public class Cart {

	@Id
	@Column(name="cart_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
	
	public void addCartItem(CartItem cartItem) {
		this.cartItems.add(cartItem);
		cartItem.setCart(this);

	}
	
	public static Cart createCart(Member member, List<CartItem> cartItemList) {
		Cart cart = new Cart();
		cart.setMember(member);
		
		for(CartItem cartItme : cartItemList) {
			cart.addCartItem(cartItme);
		}
		
		return cart;
	}
}
