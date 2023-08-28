package com.travel.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.entity.DataContent;
import com.travel.service.DataService;


@Controller
public class PlanContentController {

	@Autowired
	private DataService dataService;

	@GetMapping("/search-data")
    @ResponseBody
    public ResponseEntity<List<DataContent>> searchData(
            @RequestParam("areaCode") int selectedAreaCode,
            @RequestParam("contentType") int selectedContentType
    ) {
        List<DataContent> searchData = dataService.getDataByAreaCodeAndContentType(
            Collections.singletonList(selectedAreaCode),
            Collections.singletonList(selectedContentType)
        );

        return ResponseEntity.ok(searchData);
    }
}
