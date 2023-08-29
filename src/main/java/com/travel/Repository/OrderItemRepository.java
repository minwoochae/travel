package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
