package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Long>{
	Pay findByMemberId(Long memberId);
}
