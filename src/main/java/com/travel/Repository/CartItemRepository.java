package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
	
	List<CartItem> findByIdIn(List<Long> ids);
}
