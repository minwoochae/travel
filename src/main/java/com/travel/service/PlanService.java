package com.travel.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Dto.PlanContentDto;
import com.travel.Dto.PlanFormDto;
import com.travel.Repository.MemberRepository;
import com.travel.Repository.PlanCommunityRepository;
import com.travel.Repository.PlanContentRepository;
import com.travel.Repository.PlanRepository;
import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;
import com.travel.entity.PlanContent;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
	private final PlanContentRepository planContentRepository;
	private final PlanRepository planRepository;
	private final MemberRepository memberRepository;
	private final PlanCommunityRepository planCommunityRepository;
	
	//플랜 만들기
	public Plan setPlan(String no, PlanFormDto planFormDto) {
		Member member = memberRepository.findByEmail(no);
		LocalDateTime now = LocalDateTime.now();
		String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
		
		Plan plan = new Plan();
		plan.setMember(member);
		plan.setPlanDate(planFormDto.getPlanDate());
		plan.setPlanTitle(planFormDto.getPlanTitle());
		plan.setRegDate(regDate);
		
		Plan planResult = planRepository.save(plan);
		
		return planResult;
		
	}
	
	
	//플랜 컨텐츠 추가하기
	public PlanContent setPlanContent(PlanContentDto planContentDto, Plan plan) {
		
		List<PlanContent> planContentList = new ArrayList<>();
		PlanContent planContent = PlanContent.createContent(planContentDto, plan);
		planContentList.add(planContent);
		PlanContent savePlanContent = planContentRepository.save(planContent);
		
		return savePlanContent;
		
	}
	
//	//플랜 커뮤니티 생성하기(비공개)
//	public PlanCommunity createPlanCommunity(Plan plan, String no) {
//		Member member = memberRepository.findByEmail(no);
//		LocalDateTime now = LocalDateTime.now();
//		String regDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
//		
//		PlanCommunity planCommunity = new PlanCommunity();
//		planCommunity.setCommunityTitle(null);
//		planCommunity.setCommunityContent(null);
//		planCommunity.setCommunityRegDate(regDate);
//		planCommunity.setMember(member);
//		planCommunity.setPlan(plan);
//		PlanCommunity savePlanCommunity = planCommunityRepository.save(planCommunity);
//		
//		return savePlanCommunity;
//	}
	
	
	//플랜 찾기
	public List<Plan> findPlan(Long memberId) {
		return planRepository.findByPlanId(memberId);
	}
	
	//email을 사용해서 플랜 찾기
	public List<Plan> findPlanByEmail(String email){
		return planRepository.findByMember_Email(email);
	}
	
	//email을 사용해서 최근 3개 플랜 가져오기
	public List<Plan> findPlanTopByEmail(String email){
		return planRepository.findTop3ByMember_EmailOrderByRegDateDesc(email);
	}
	
	//최근 플랜 찾기
	public Plan findLastPlan(String memberNo, Pageable pageable){
	    Member member = memberRepository.findByEmail(memberNo);
	    if (member == null) {
	        return null; // Or throw an exception
	    }
	    Long memberId = member.getId();

	    List<Plan> plans = planRepository.findLatestByMemberId(memberId, pageable);
	    return plans.isEmpty() ? null : plans.get(0);
	}
	
	//플랜 컨텐츠 찾기
	public List<PlanContent> findPlanContentsByPlanId(Long planId){
		return planContentRepository.findByPlan_Id(planId);
	}
	
	//플랜 리스트 불러오기
	public Page<Plan> getPlansByEmail(String email, Pageable pageable) {
	    return planRepository.findPlansByEmailOrderByRegDateDesc(email, pageable);
	}
	
	public PlanFormDto getPlanDtl(Long planId) {
		Plan plan = planRepository.findById(planId).orElseThrow(EntityNotFoundException::new);
		PlanFormDto planFormDto = PlanFormDto.of(plan);
		List<PlanContent> planContentList = planContentRepository.findByPlan_Id(planId);
		planFormDto.setPlanContentDtoList(planContentList);
		
		return planFormDto;
	}
	
	
	public Plan getPlanById(Long planId) {
        return planRepository.findById(planId).orElseThrow(EntityNotFoundException::new);
    }
	
	public Page<PlanCommunity> findPaginated(Pageable pageable) {
	    return planCommunityRepository.findAllByOrderByCommunityRegDateDesc(pageable);
	} 
	
	
	
	public void deleteplan(Long planId) {
		Plan plan = planRepository.findById(planId).orElseThrow(EntityNotFoundException::new);

		planRepository.delete(plan);
	}
	
	
	
	
	
	
}
