package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}
