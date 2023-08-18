package com.travel.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.CartDto;
import com.travel.service.ItemService;
import com.travel.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {
	private final OrderService orderService;
	
	@PostMapping(value = "/cart")
	public @ResponseBody ResponseEntity cart(@RequestBody @Valid CartDto orderDto, BindingResult bindingResult, Principal principal) {
		System.out.println("여기는 온건가???");
		String email = principal.getName();
		Long cartId;
		
		try {
			System.out.println("여기는??????");
			cartId = orderService.cart(orderDto, email);			
			System.out.println("또 여기는??????");
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Long>(cartId, HttpStatus.OK);

	
	}
	
}
