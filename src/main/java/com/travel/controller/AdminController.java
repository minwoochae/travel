package com.travel.controller;

import java.security.Principal;
import java.util.Optional;

import com.travel.Dto.MemberFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.MemberFormDto;
import com.travel.entity.Member;
import com.travel.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AdminController {
	private final MemberService memberService;
	@GetMapping(value = "/admin")
	public String admin() {
		return "/admin/adminMain";
	}
	
	//회원 리스트

		@GetMapping(value = {"/admin/list", "/admin/list/{page}"})
		public String memberManage(@PathVariable("page") Optional<Integer> page, Model model ) {
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10); 
			Page<Member> travel = memberService.getAdminlistPage(pageable);
			model.addAttribute("travel", travel);
			model.addAttribute("maxPage", 5); 
			
			return "admin/MemberList";
		}
		
		
		@GetMapping(value =  {"/admin/profile" , "/admin/profile/{memberId}"})
		public String  Profilemember(@PathVariable("memberId") Long memberId, Model model) {
			
				MemberFormDto memberFormDto = memberService.getmemberDtl(memberId);
				
				model.addAttribute("member", memberFormDto);
			return "admin/profile";
		}

		
	// 쇼핑몰 상품 등록하기
	@GetMapping(value="/adminShop")
	public String adminShop() {
		return "/admin/itemRegist";
	}
	
	//회원 탈퇴시키기
	@DeleteMapping(value ="admin/{memberId}/delete")
	public @ResponseBody ResponseEntity  deleteMember(@RequestBody @PathVariable("memberId") Long memberId,
			Principal principal) {
		
		memberService.deleteMember(memberId);
		
		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}
}
