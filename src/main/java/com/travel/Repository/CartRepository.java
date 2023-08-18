package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findByMemberId(Long memberId);
}
