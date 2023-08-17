package com.travel.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{

}
