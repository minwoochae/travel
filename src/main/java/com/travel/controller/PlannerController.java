package com.travel.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.PlanContentDto;
import com.travel.Dto.PlanFormDto;
import com.travel.service.PlanService;

import jakarta.validation.Valid;
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
	@PostMapping(value = "/planner/setplan")
	public @ResponseBody ResponseEntity createPlan(Principal principal, Model model,@RequestBody @Valid PlanFormDto planFormDto, BindingResult bindingResult) {
		String no = principal.getName();
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage());
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		try {
			planService.setPlan(no, planFormDto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(no, HttpStatus.OK);
	}
	
}
