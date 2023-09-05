package com.travel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.travel.Dto.OrderDto;
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
import jakarta.persistence.ManyToOne;
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
	
	@Column(name = "total_price")
	private int totalPrice;
	
	private String orderDate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pay_id")
	private Pay pay;
	
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;
    


	public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrders(null);
    }
    
	//order객체를 생성해준다.
	public static Orders createOrder(Member member, List<OrderItem> orderItemList) {
		Orders order = new Orders();
		order.setMember(member);
		
		for(OrderItem otderItem : orderItemList) {
			order.addOrderItem(otderItem);
		}
		
		order.setOrderStatus(OrderStatus.ORDER);
		
		return order;
	}
	
	//주문취소
	public void cancelOrder() {
		this.orderStatus = OrderStatus.CANCEL;
		
		//재고 원래대로
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
	
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(id, orders.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}