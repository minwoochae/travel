package com.travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.service.PlanContentService;

@Controller
public class PlanContentController {

    @Autowired
    private PlanContentService planContentService;

    @GetMapping("/save-content")
    @ResponseBody
    public String DataSave() {
        try {
            planContentService.DataSave();
            return "Content saved from API to database.";
        } catch (Exception e) {
            return "Error saving content from API: " + e.getMessage();
        }
    }
}
