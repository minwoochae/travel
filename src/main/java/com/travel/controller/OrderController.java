package com.travel.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Repository.CartItemRepository;
import com.travel.entity.CartItem;
import com.travel.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping(value= "/order/address")
	public @ResponseBody ResponseEntity getAddress(@RequestBody Long[] selectedProductIds,BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메세지를 합친다.
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("오긴함?");
	    System.out.println(selectedProductIds);
	    
	    for (Long productId : selectedProductIds) {
	        System.out.println("Product ID: " + productId);
	    }

//	    List<CartItem> selectedItems = orderService.findByIdIn(Arrays.asList(selectedProductIds));	        // 선택된 상품들의 정보를 모델에 추가하거나 필요한 작업 수행
//	        model.addAttribute("selectedItems", selectedItems);

//	    System.out.println(selectedItems);
	    return new ResponseEntity<Long[]>(selectedProductIds, HttpStatus.OK);	
	    
	}
	
	@GetMapping(value= "/order/test/{selectedProductIds}")
	public String test(@PathVariable("selectedProductIds") String selectedProductIds) {
		
		String[] idsArray = selectedProductIds.split(",");
        Long[] ids = new Long[idsArray.length];
        
        for (int i = 0; i < idsArray.length; i++) {
            ids[i] = Long.parseLong(idsArray[i]);
            System.out.println(ids[i] + "FFFFFFFFF");
        }
        
		return "/item/address";
	}
    
}
