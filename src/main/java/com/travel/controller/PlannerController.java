package com.travel.controller;

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

	@GetMapping(value = "/planner/list")
	public String planList() {
		return "planner/planList";
	}
	
	@GetMapping(value="/planComplete")
	public String planComp() {
		return "planner/planComp";
	}
	
	
	
}
