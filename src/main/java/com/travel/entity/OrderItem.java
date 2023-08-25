package com.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item") 
@Getter
@Setter
@ToString
public class OrderItem {

	@Id
	@Column(name = "order_item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_item_price")
	private int orderPrice;
	
	@Column(name = "order_item_count")
	private int orderCount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	private Orders orders;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;
	
}
