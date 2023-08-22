package com.travel.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.travel.Dto.MainTourDto;
import com.travel.Dto.TourFormDto;
import com.travel.Dto.TourSearchDto;
import com.travel.service.TourService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TourController {

	private final TourService tourService;
	
	// 리스트에 뿌려주기
	@GetMapping(value="/adminTour/list")
	public String itemShopList(Model model,TourSearchDto tourSearchDto, Optional<Integer> page) {
		Pageable pageable = PageRequest.of(page.isPresent()?page.get():0, 6); 
		Page<MainTourDto> tours = tourService.getMainTourPage(tourSearchDto, pageable);
		
		model.addAttribute("tours",tours);
		model.addAttribute("tourSearchDto",tourSearchDto);
		model.addAttribute("maxPage",5);
		
		return "admin/tourList";
	}
	
	
	

}
