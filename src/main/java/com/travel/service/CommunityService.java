package com.travel.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	// 커뮤니티 작성
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
	
	public PlanCommunityDto getCommunityDtl(Long communityId) {
		PlanCommunity planCommunity = planCommunityRepository.findById(communityId).orElseThrow(EntityNotFoundException::new);
		PlanCommunityDto planCommunityDto = PlanCommunityDto.of(planCommunity);
		
		
		return planCommunityDto;
	}
	
	
	//email을 이용해서 최근 3개 커뮤니티 작성 글 가져오기
	public List<PlanCommunity> getTop3RecentCommunitiesByMemberEmail(String email) {
	    return planCommunityRepository.findTop3ByMemberEmailOrderByCommunityRegDateDesc(email);
	}
	
	//플랜 리스트에 사용될 email로 플랜 전체 가져오기
	public Page<PlanCommunity> getCommunitiesByMemberEmail(String email, Pageable pageable) {
	    return planCommunityRepository.findByMemberEmailOrderByCommunityRegDateDesc(email, pageable);
	}

	//커뮤니티 삭제하기
	public void deleteCommunity(Long communityId) {
		PlanCommunity planCommunity = planCommunityRepository.findById(communityId).orElseThrow(EntityNotFoundException::new);
		
		planCommunityRepository.delete(planCommunity);
	}
	

}
