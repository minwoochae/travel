package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.PlanContent;

public interface PlanContentRepository extends JpaRepository<PlanContent, Long>{
	List<PlanContent> findByPlan_Id(Long planId);

}
