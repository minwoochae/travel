package com.travel.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.Dto.PlanContentDto;
import com.travel.Dto.PlanFormDto;
import com.travel.entity.Plan;
import com.travel.service.PlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {
	private final PlanService planService;
	private final ObjectMapper objectMapper;
	

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
	
	//플랜만들기
	@PostMapping(value = "/planner/setplan")
	public @ResponseBody ResponseEntity createPlan(Principal principal, Model model, @RequestBody HashMap<String, Object> hashMap, BindingResult bindingResult) {
		String no = principal.getName();
		PlanFormDto planFormDto = new PlanFormDto();
		PlanContentDto planContentDto = new PlanContentDto();
		
		
		try {
			
			if (hashMap.containsKey("param")) {
				String jsonData = (String) hashMap.get("param");
				
				
				try {
					HashMap<String, Object> paramData = objectMapper.readValue(jsonData, HashMap.class);
	                planFormDto.setPlanTitle((String)paramData.get("planTitle"));
	                planFormDto.setPlanDate((String)paramData.get("planDate"));
	                
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
			Plan plan = planService.setPlan(no, planFormDto);
			
			if (hashMap.containsKey("paramContent")) {
				String jsonData = (String) hashMap.get("paramContent");
				List<Map<String, Object>> paramData = objectMapper.readValue(jsonData, new TypeReference<List<Map<String, Object>>>() {});

				for(Map data : paramData) {
					
                	planContentDto.setPlaceName((String)data.get("placeName"));
                	planContentDto.setTravelDivision((String)data.get("travelDivision"));
                	planContentDto.setPlanDay((String)data.get("planDay"));
                	planContentDto.setPlaceAddress((String)data.get("placeAddress"));
                	planContentDto.setPlaceLatitude((String)data.get("placeLatitude"));
                	planContentDto.setPlaceLongitude((String)data.get("placeLongitude"));
                	planContentDto.setPlace_img((String)data.get("place_img"));
                	
                	planService.setPlanContent(planContentDto, plan);
                }
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<String>(no, HttpStatus.OK);
	}
	
}
