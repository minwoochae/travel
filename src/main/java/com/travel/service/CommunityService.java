package com.travel.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.PlanCommunityRepository;
import com.travel.Repository.PlanRepository;
import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
	
	private final MemberRepository memberRepository;
	private final PlanRepository planRepository;
	private final PlanCommunityRepository planCommunityRepository;
	
	public Long saveCommunity(PlanCommunityDto planCommunityDto, String memberId, Long planId) throws Exception {
		Member member = memberRepository.findByEmail(memberId);
		LocalDateTime now = LocalDateTime.now();
		String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		Optional<Plan> optionalPlan = planRepository.findById(planId);
	    if (!optionalPlan.isPresent()) {
	        // 예외 처리
	        throw new Exception("Plan not found");
	    }
	    Plan plan = optionalPlan.get();
		PlanCommunity planCommunity = planCommunityDto.createPlanCommunity(regDate, member, plan);
		planCommunityRepository.save(planCommunity);
		
		return planCommunity.getId();
	}
	
}
