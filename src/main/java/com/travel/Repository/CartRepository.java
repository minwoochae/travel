package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findByMemberId(Long memberId);
	
	@Query("select o from Cart o where o.member.email = :email order by o.id desc")
	List<Cart> findCarts(@Param("email") String email, Pageable pageable);

	@Query("select count(o) from Cart o where o.member.email = :email")
	Long countCart(@Param("email") String email);
}
