package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.travel.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long>{
	
	@Query("SELECT p FROM Plan p WHERE p.member.id = :memberId")
	List<Plan> findByPlanId(@Param("memberId") Long memberId);
	
	@Query("SELECT p FROM Plan p WHERE p.member.id = :memberId ORDER BY p.regDate DESC")
	List<Plan> findLatestByMemberId(@Param("memberId") Long memberId, Pageable pageable);

	List<Plan> findByMember_Email(String email);


}
