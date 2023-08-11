package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {

<<<<<<< HEAD
	@GetMapping(value ="/planner")
	public String plannerMain() {
		return "planner/plannerMain";
=======
	@GetMapping(value = "/planner")
	public String main() {
		return "/planner/plannerMain";
>>>>>>> 1f9fff599f297638e4fd132c0a4d0634c26c6e34
	}
	
	
}
