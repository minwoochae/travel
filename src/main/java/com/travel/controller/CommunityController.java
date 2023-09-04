package com.travel.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.PlanCommunityDto;
import com.travel.Dto.PlanFormDto;
import com.travel.auth.PrincipalDetails;
import com.travel.entity.Member;
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

	//커뮤니티 작성 페이지
	@GetMapping(value = { "/community/write", "/community/write/{planId}" })
	public String createCommunityPage(@PathVariable("planId") Long planId, Model model,
			PlanCommunityDto planCommunityDto) {
		PlanFormDto planFormDto = planService.getPlanDtl(planId);
		model.addAttribute("plan", planFormDto);
		model.addAttribute("planCommunityDto", new PlanCommunityDto() );

		return "community/writeCommunity";
	}
	
	//커뮤니티 글 작성
	@PostMapping(value = "/community/write")
	public String createCommunity(@Valid PlanCommunityDto planCommunityDto, Model model, Authentication authentication,
				@RequestParam("planId") Long planId) {
		
		try {
			  PrincipalDetails principals = (PrincipalDetails) authentication.getPrincipal();
		        Member members = principals.getMember();
				String memberNo = members.getEmail();
			communityService.saveCommunity(planCommunityDto, memberNo, planId);

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "작성 에러");
			return "redirect:/";
		}

		return "redirect:/";
	}

	//커뮤니티 수정 페이지
	@GetMapping(value = { "/community/update", "/community/update/{communityId}" })
	public String updateCommunityPage(@PathVariable("communityId") Long communityId, Model model
			) {
		try {
			
			PlanCommunityDto planCommunityDto = communityService.getCommunityDtl(communityId);
			PlanCommunity planCommunity = communityService.getCommunity(communityId);
			planCommunityDto.setId(planCommunity.getId());
			model.addAttribute("community", planCommunityDto);
			model.addAttribute("planCommunityDto", new PlanCommunityDto() );
			PlanFormDto planFormDto = planService.getPlanDtl(planCommunityDto.getPlan().getId());
			model.addAttribute("plan", planFormDto);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "페이지 에러");
			model.addAttribute("community", new PlanCommunityDto());
			return "/";
		}
		
		return "community/updateCommunity";
	}
	
	//커뮤니티 수정
	@PostMapping(value = "/community/update")
	public String updateCommunity(@PathVariable("communityId") Long communityId, @Valid PlanCommunityDto planCommunityDto, Model model) {
		
//		if (planCommunityDto.getId() == null) {
//			System.out.println(planCommunityDto.getId());
//			PlanCommunity planCommunity = communityService.getCommunity(communityId);
//			System.out.println(planCommunity);
//	        model.addAttribute("errorMessage", "Invalid request: ID is missing");
//	        return "redirect:/";
//	    }
		
		try {
			PlanCommunity planCommunity = communityService.getCommunity(communityId);
			planCommunityDto.setId(planCommunity.getId());
			communityService.updateCommunity(planCommunityDto);
		} catch (Exception e) {
			
			e.printStackTrace();
			model.addAttribute("errorMessage", "작성 에러");
		}
		
		return "redirect:/";
	}
	
	

	//커뮤니티 리스트
	@GetMapping(value = {"/community/viewCommunityList", "/community/viewCommunityList/{page}"})
	public String viewCommunityList(Authentication authentication, Model model, Pageable pageable, @PathVariable Optional<Integer> page) {
		 PrincipalDetails principals = (PrincipalDetails) authentication.getPrincipal();
	        Member members = principals.getMember();
			String email = members.getEmail();
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
	
	
	// 커뮤니티삭제하기
		@DeleteMapping(value = "/community/delete/{communityId}")
		public @ResponseBody ResponseEntity deleteplan(@RequestBody @PathVariable("communityId") Long communityId,
				Principal principal) {

			communityService.deleteCommunity(communityId);
			
			return new ResponseEntity<Long>(communityId, HttpStatus.OK);
		}
	
	
}
