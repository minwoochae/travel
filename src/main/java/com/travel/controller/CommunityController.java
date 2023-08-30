package com.travel.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Dto.PlanFormDto;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;
import com.travel.service.CommunityService;
import com.travel.service.MemberService;
import com.travel.service.PlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CommunityController {
	private final PlanService planService;
	private final MemberService memberService;
	private final CommunityService communityService;

	@GetMapping(value = { "/community/write", "/community/write/{planId}" })
	public String createCommunityPage(@PathVariable("planId") Long planId, Model model, Principal principal,
			PlanCommunityDto planCommunityDto) {
		String no = principal.getName();
		PlanFormDto planFormDto = planService.getPlanDtl(planId);
		model.addAttribute("plan", planFormDto);
		model.addAttribute("planCommunityDto", new PlanCommunityDto() );

		return "community/writeCommunity";
	}

	@GetMapping(value = { "/community/update", "/community/update/{planId}" })
	public String updateCommunityPage(@PathVariable("planId") Long planId, Model model, Principal principal,
			PlanCommunityDto planCommunityDto) {
		String no = principal.getName();
		PlanFormDto planFormDto = planService.getPlanDtl(planId);
		model.addAttribute("plan", planFormDto);
		model.addAttribute("planCommunityDto", new PlanCommunityDto() );

		return "community/updateCommunity";
	}
	
	@PostMapping(value = "/community/write")
	public String createCommunity(@Valid PlanCommunityDto planCommunityDto, Model model, Principal principal,
				@RequestParam("planId") Long planId) {
		
		try {
			String no = principal.getName();
			communityService.saveCommunity(planCommunityDto, no, planId);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "작성 에러");
			return "redirect:/";
		}

		return "redirect:/";
	}
	
	//커뮤니티 리스트
	@GetMapping(value = {"/community/viewCommunityList", "/community/viewCommunityList/{page}"})
	public String viewCommunityList(Principal principal, Model model, Pageable pageable, @PathVariable Optional<Integer> page) {
		String email = principal.getName();
		Page<PlanCommunity> planCommunitys = communityService.getCommunitiesByMemberEmail(email, PageRequest.of(page.isPresent() ? page.get() : 0, 5));
		model.addAttribute("community", planCommunitys);
		model.addAttribute("maxPage", 5);
		
		return "community/myCommunityList";
	}

	//커뮤니티 작성 글 상세보기
	@GetMapping(value = {"/community/viewCommunityInfo", "/community/viewCommunityInfo/{communityId}"})
	public String viewCommunityInfo(@PathVariable("communityId") Long communityId, Model model) {
		PlanCommunityDto planCommunityDto = communityService.getCommunityDtl(communityId);
		model.addAttribute("community", planCommunityDto);
		return "community/myCommunityInfo";
	}
	
	
}
