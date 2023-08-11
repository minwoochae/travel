package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {

	@GetMapping(value ="/planner")
	public String plannerMain() {
		return "planner/plannerMain";
	}
	
	
}
