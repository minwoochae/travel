package com.travel.entity;

import java.util.ArrayList;
import java.util.List;

import com.travel.constant.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
public class Orders{

	@Id
	@Column(name = "orders_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Column(name = "order_info_name")
	private String orderInfoName;
	
	@Column(name = "order_info_address")
	private String orderInfoAddress;
	
	@Column(name = "order_info_phone")
	private String orderInfoPhone;
	
	@Column(name= "zip_code")
	private String zipCode;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_id")
	private Pay pay;
	
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    public Orders(Long[] orderItemIds,  String orderName, String zipCode, String orderAddress, String phoneNumber) {
        this.orderStatus = OrderStatus.ORDER;
        this.orderInfoName = orderName;
        this.orderInfoAddress = orderAddress;
        this.orderInfoPhone = phoneNumber;
        this.zipCode = zipCode;
		/*
		 * for (Long orderItemId : orderItemIds) { OrderItem orderItem = new
		 * OrderItem(orderItemId); this.addOrderItem(orderItem); }
		 */
    }


	public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrders(null);
    }
}
