package com.travel.controller;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Repository.DataRepository;
import com.travel.entity.PlanCommunity;
import com.travel.service.PlanContentService;
import com.travel.service.PlanService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PlanContentService planContentService;
    private final PlanService planService;

    @GetMapping(value = "/")
    public String main(Authentication authentication, Model model, Optional<Integer> page) {
    	Page<PlanCommunity> planCommunity = planService.findPaginated(PageRequest.of(page.isPresent() ? page.get() : 0, 6));
    	model.addAttribute("community", planCommunity);
		model.addAttribute("maxPage", 5);
    	
        return "/main";
    }
    

    @GetMapping(value = "/load/data")
    @ResponseBody // 반환값을 응답 본문으로 사용
    public void loadData() {    	
        long recordCount = planContentService.recordCount();
        
        if (recordCount == 0) {
        	planContentService.DataSave();
        }
    }
    
}
