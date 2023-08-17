package com.travel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.PlanContentDto;
import com.travel.Repository.PlanContentRepository;
import com.travel.entity.PlanContent;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
	private final PlanContentRepository planContentRepository;
	
	
	//플랜 만들기
	public PlanContent setPlanContent(PlanContentDto planContentDto) {
		
		PlanContent planContent = PlanContent.createContent(planContentDto);
		PlanContent savePlanContent = planContentRepository.save(planContent);
		
		return savePlanContent;
		
	}
}
