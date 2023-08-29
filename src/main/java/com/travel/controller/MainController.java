package com.travel.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Repository.DataRepository;
import com.travel.service.PlanContentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final PlanContentService planContentService;
    private final DataRepository dataRepository;

    @GetMapping(value = "/")
    public String main() {
        return "/main";
    }
    

    @GetMapping(value = "/load-data")
    @ResponseBody // 반환값을 응답 본문으로 사용
    public void loadData() {
        long recordCount = dataRepository.count();
        
        if (recordCount == 0) {
            planContentService.DataSave();
        }
        

    }
    
}
