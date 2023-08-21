package com.travel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.PlanContentDto;
import com.travel.Dto.PlanFormDto;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.PlanContentRepository;
import com.travel.Repository.PlanRepository;
import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanContent;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
	private final PlanContentRepository planContentRepository;
	private final PlanRepository planRepository;
	private final MemberRepository memberRepository;
	
	//플랜 만들기
	public Long setPlan(String no, PlanFormDto planFormDto) {
		Member member = memberRepository.findByEmail(no);
		
		Plan plan = new Plan();
		plan.setMember(member);
		plan.setPlanDate(planFormDto.getPlanDate());
		plan.setPlanTitle(planFormDto.getPlanTitle());
		
		Plan planResult = planRepository.save(plan);
		
		return planResult.getId();
		
	}
	
	
	//플랜 컨텐츠 추가하기
	private PlanContent setPlanContent(PlanContentDto planContentDto) {
		
		PlanContent planContent = PlanContent.createContent(planContentDto);
		PlanContent savePlanContent = planContentRepository.save(planContent);
		
		return savePlanContent;
		
	}
	
	
	
	
}
