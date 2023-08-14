package com.travel.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.travel.entity.Member;
import com.travel.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	private final MemberService memberService;
	@GetMapping(value = "/admin")
	public String admin() {
		return "/admin/adminMain";
	}
	
		@GetMapping(value = {"/admin/list", "/admin/list/{page}"})
		public String memberManage(@PathVariable("page") Optional<Integer> page, Model model ) {
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10); 
			Page<Member> travel = memberService.getAdminlistPage(pageable);
			model.addAttribute("travel", travel);
			model.addAttribute("maxPage", 5); 
			
			return "admin/MemberList";
		}
		
		
		@GetMapping(value =  {"/admin/profile" , "/admin/profile/{memberId}"})
		public String  Profilemember(Model model) {
			
			
			return "admin/profile";
}
	 
		
}
	
