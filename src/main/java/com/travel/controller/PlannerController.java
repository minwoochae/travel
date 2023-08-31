package com.travel.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.Dto.PlanContentDto;
import com.travel.Dto.PlanFormDto;
import com.travel.auth.PrincipalDetails;
import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;
import com.travel.entity.PlanContent;
import com.travel.service.MemberService;
import com.travel.service.PlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PlannerController {
	private final PlanService planService;
	private final MemberService memberService;
	private final ObjectMapper objectMapper;

	@GetMapping(value = "/planner")
	public String plannerMain() {
		return "planner/plannerMain";
	}

	@GetMapping(value = { "/planner/list", "/planner/list/{page}" })
	public String planList(Model model, @PathVariable Optional<Integer> page) {
		Page<PlanCommunity> planCommunity = planService
				.findPaginated(PageRequest.of(page.isPresent() ? page.get() : 0, 6));
		model.addAttribute("community", planCommunity);
		model.addAttribute("maxPage", 5);
		return "planner/planList";
	}

	// 플랜 완성 페이지
	@GetMapping(value = "/planComplete")
	public String planComp(Authentication authentication, Model model, Optional<Integer> page) {
		PrincipalDetails principals = (PrincipalDetails) authentication.getPrincipal();
		Member members = principals.getMember();
		String memberNo = members.getEmail();

		Pageable pageable = PageRequest.of(page.orElse(0), 1);

		Plan Plan = planService.findLastPlan(memberNo, pageable);
		model.addAttribute("plan", Plan);

		if (Plan != null) {
			List<PlanContent> PlanContents = planService.findPlanContentsByPlanId(Plan.getId());
			model.addAttribute("PlanContents", PlanContents);

			Map<String, List<PlanContent>> planContentByDay = new HashMap<>();
			for (PlanContent content : PlanContents) {
				String day = content.getPlanDay(); // 가정: PlanContent 클래스에 getPlanDay 메소드가 있다.
				planContentByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(content);
			}
			model.addAttribute("planContentByDay", planContentByDay);
		}

		return "planner/planComp";
	}

	// 플랜만들기
	@PostMapping(value = "/planner/setplan")
	public @ResponseBody ResponseEntity createPlan(Authentication authentication, Model model,
			@RequestBody HashMap<String, Object> hashMap, BindingResult bindingResult) {
		// 로그인하지 않은 사용자에 대한 처리
		if (authentication == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		PrincipalDetails principals = (PrincipalDetails) authentication.getPrincipal();
		Member members = principals.getMember();
		String no = members.getEmail();
		PlanFormDto planFormDto = new PlanFormDto();
		PlanContentDto planContentDto = new PlanContentDto();

		try {

			if (hashMap.containsKey("param")) {
				String jsonData = (String) hashMap.get("param");

				try {
					HashMap<String, Object> paramData = objectMapper.readValue(jsonData, HashMap.class);
					planFormDto.setPlanTitle((String) paramData.get("planTitle"));
					planFormDto.setPlanDate((String) paramData.get("planDate"));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Plan plan = planService.setPlan(no, planFormDto);
			if (hashMap.containsKey("paramContent")) {
				String jsonData = (String) hashMap.get("paramContent");
				List<Map<String, Object>> paramData = objectMapper.readValue(jsonData,
						new TypeReference<List<Map<String, Object>>>() {
						});

				for (Map data : paramData) {

					planContentDto.setPlaceName((String) data.get("placeName"));
					planContentDto.setTravelDivision((String) data.get("travelDivision"));
					planContentDto.setPlanDay((String) data.get("planDay"));
					planContentDto.setPlaceAddress((String) data.get("placeAddress"));
					planContentDto.setPlaceTel((String) data.get("placeTel"));
					planContentDto.setPlaceLatitude((String) data.get("placeLatitude"));
					planContentDto.setPlaceLongitude((String) data.get("placeLongitude"));
					planContentDto.setPlace_img((String) data.get("place_img"));

					planService.setPlanContent(planContentDto, plan);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(no, HttpStatus.OK);
	}

	// 플랜 리스트
	@GetMapping(value = { "/planner/myPlanList", "/planner/myPlanList/{page}" })
	public String myPlanList(Authentication authentication, Model model, Pageable pageable,
			@PathVariable Optional<Integer> page) {
		PrincipalDetails principals = (PrincipalDetails) authentication.getPrincipal();
		Member members = principals.getMember();
		String email = members.getEmail();
		System.out.println(email);
		Page<Plan> plans = planService.getPlansByEmail(email, PageRequest.of(page.isPresent() ? page.get() : 0, 5));
		model.addAttribute("plan", plans);
		model.addAttribute("maxPage", 5);

		return "planner/myPlanList";
	}

	// 플랜 상세보기
	@GetMapping(value = { "/planner/myPlanInfo", "/planner/myPlanInfo/{planId}" })
	public String myPlanInfo(@PathVariable("planId") Long planId, Model model) {
		PlanFormDto planFormDto = planService.getPlanDtl(planId);
		model.addAttribute("plan", planFormDto);
		Plan plan = planService.getPlanById(planId);
		model.addAttribute("planData", plan);

		return "planner/myPlanInfo";
	}

	// 공유받은 플랜보기
	@GetMapping(value = { "/planner/sharePlan", "/planner/sharePlan/{planId}" })
	public String sharePlan(@PathVariable("planId") Long planId, Model model) {
		PlanFormDto planFormDto = planService.getPlanDtl(planId);
		model.addAttribute("plan", planFormDto);
		Plan plan = planService.getPlanById(planId);
		model.addAttribute("planData", plan);

		return "planner/sharePlan";
	}

	// 플랜삭제하기
	@DeleteMapping(value = "/planner/delete/{planId}")
	public @ResponseBody ResponseEntity deleteplan(@RequestBody @PathVariable("planId") Long planId) {

		planService.deleteplan(planId);

		return new ResponseEntity<Long>(planId, HttpStatus.OK);
	}

}