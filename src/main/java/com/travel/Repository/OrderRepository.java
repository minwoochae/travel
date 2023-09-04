package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{
	//현재 로그인한 사용자의 주문 데이터를 페이징 조건에 맞춰서 조회
	@Query("SELECT o FROM Orders o WHERE o.member.email = :email ORDER BY o.pay.payDate DESC")
	List<Orders> findOrdersByEmail(@Param("email") String email, Pageable pageable);

	@Query("select count(o) from Orders o where o.member.email = :email")
	Long countOrders(@Param("email") String eamil);
}
