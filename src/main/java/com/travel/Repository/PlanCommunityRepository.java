package com.travel.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.Dto.PlanCommunityDto;
import com.travel.entity.PlanCommunity;

@Repository
public interface PlanCommunityRepository extends JpaRepository<PlanCommunity, Long>{
	
	List<PlanCommunity> findByMemberEmail(String email);

	List<PlanCommunity> findTop3ByMemberEmailOrderByCommunityRegDateDesc(String email);

	Page<PlanCommunity> findByMemberEmail(String email, Pageable pageable);
	
	Optional<PlanCommunity> findById(Long id);

	Page<PlanCommunity> findAll(Pageable pageable);
}
