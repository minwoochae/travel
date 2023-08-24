package com.travel.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.travel.Dto.CartItemDto;
import com.travel.Dto.ItemImgDto;
import com.travel.Dto.OrderItemDto;
import com.travel.Repository.CartItemRepository;
import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@PostMapping(value= "/order/address")
	public @ResponseBody ResponseEntity getAddress(@RequestBody Map<String, Object> requestData,BindingResult bindingResult, Model model) {
	    Long[] selectedProductIds = ((List<Integer>) requestData.get("selectedProductIds"))
	            .stream()
	            .map(Long::valueOf)
	            .toArray(Long[]::new);
	    
	    List<String> imgUrlArray = (List<String>) requestData.get("imgUrlArray");
		
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메세지를 합친다.
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("imgUrlArray", imgUrlArray);
	    responseData.put("selectedProductIds", selectedProductIds);
	    
	    System.out.println(responseData);

	    return new ResponseEntity<>(responseData, HttpStatus.OK);	
	    
	}
	
	@GetMapping(value= "/order/{selectedProductIdsString}")
	public String test(@RequestParam("selectedProductIdsString") String selectedProductIdsString,
            @RequestParam("imgUrls") String imgUrls,
            Model model) {

		String[] idsArray = selectedProductIdsString.split(",");
        Long[] ids = new Long[idsArray.length];
        
        String[] imgsArray = imgUrls.split(",");
        
        for (int i = 0; i < idsArray.length; i++) {
            ids[i] = Long.parseLong(idsArray[i]);
        }
        
        
        long totalPrice = 0;
        List<CartItemDto> selectedItems = orderService.findItemsByIds(ids);

        
        for (CartItemDto item : selectedItems) {
            totalPrice += item.getPrice() * item.getCount();
        }
        
        model.addAttribute("selectedItems", selectedItems);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("imgsArray", imgsArray);
        
        return "/item/address";
	}
    
}
