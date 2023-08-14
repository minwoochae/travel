package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	@GetMapping(value = "/admin")
	public String admin() {
		return "/admin/adminMain";
	}
	
	@GetMapping(value="/adminShop")
	public String adminShop() {
		return "/admin/itemRegist";
	}
}
