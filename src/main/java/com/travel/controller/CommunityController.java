package com.travel.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Dto.PlanFormDto;
import com.travel.entity.Plan;
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

}
