package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {

<<<<<<< HEAD
=======

>>>>>>> be57870aa53021c5701a13b7ef145365fd263bea
	@GetMapping(value ="/planner")
	public String plannerMain() {
		return "planner/plannerMain";
	}

	
}
