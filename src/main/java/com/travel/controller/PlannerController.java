package com.travel.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.PlanContentDto;
import com.travel.service.PlanService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {
	private final PlanService planService;
	

	@GetMapping(value ="/planner")
	public String plannerMain() {
		return "planner/plannerMain";
	}

	@GetMapping(value = "/planList")
	public String planList() {
		return "planner/planList";
	}
	
	@GetMapping(value="/planComplete")
	public String planComp() {
		return "planner/planComp";
	}
	
	//플랜만들기
	public @ResponseBody ResponseEntity createPlan(Principal principal, Model model) {
		String no = principal.getName();
		
		try {
			planService.setPlan(no);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return new ResponseEntity<String>(no, HttpStatus.OK);
	}
	
}
