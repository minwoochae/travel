package com.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	
	@GetMapping(value="/itemList")
	public String itemList() {
		return "/item/itemShopList";
	}
	
	@GetMapping(value="/itemDtl")
	public String itemDtl() {
		return "/item/itemDtl";
	}
	
	@GetMapping(value = "/cart")
	public String cart() {
		return "/item/cart";
	}
}
