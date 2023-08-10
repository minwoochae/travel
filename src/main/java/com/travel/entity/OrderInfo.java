package com.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_info") 
@Getter
@Setter
@ToString
public class OrderInfo {

	@Id
	@Column(name = "order_info_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "order_info_name")
	private String orderInfoName;
	
	@Column(name = "order_info_address")
	private String orderInfoAddress;
	
	@Column(name = "order_info_phone")
	private String orderInfoPhone;
	
	@OneToOne
	@JoinColumn(name = " orders_id")
	private Orders orders;
	
}
