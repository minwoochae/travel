package com.travel.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.PlanCommunityRepository;
import com.travel.Repository.PlanRepository;
import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {
	
	private final MemberRepository memberRepository;
	private final PlanRepository planRepository;
	private final PlanCommunityRepository planCommunityRepository;
	
	public Long saveCommunity(PlanCommunityDto planCommunityDto, String memberId) throws Exception {
		Member member = memberRepository.findByEmail(memberId);
		LocalDateTime now = LocalDateTime.now();
		String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		PlanCommunity planCommunity = planCommunityDto.createPlanCommunity(regDate, member);
		planCommunityRepository.save(planCommunity);
		
		return planCommunity.getId();
	}
	
}
