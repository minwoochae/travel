package com.travel.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.CartItemDto;
import com.travel.Dto.OrderHistDto;
import com.travel.service.OrderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


	@PostMapping(value= "/order/address")
	public @ResponseBody ResponseEntity getAddress(@RequestBody Map<String, Object> requestData,BindingResult bindingResult, Model model) {
	    Long[] selectedProductIds = ((List<Integer>) requestData.get("selectedProductIds"))
	            .stream()
	            .map(Long::valueOf)
	            .toArray(Long[]::new);
		if(bindingResult.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			
			for(FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage()); //에러메세지를 합친다.
			}
			
			return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
		}
		
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("selectedProductIds", selectedProductIds);
	    

	    return new ResponseEntity<>(responseData, HttpStatus.OK);	
	    
	}
	
	@GetMapping(value= "/order/{selectedProductIdsString}")
	public String test(@PathVariable("selectedProductIdsString") Object selectedProductIdsString,
            Model model) {
		String[] idsArray = selectedProductIdsString.toString().split(",");
        Long[] ids = new Long[idsArray.length];
        
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

        return "/item/address";
	}
	

	@GetMapping(value = "/order/success/{payId}")
	public String orderSuccess() {
		
		return "/item/orderInfo";
	}
	
	
	@GetMapping(value = {"/orders", "/orders/{page}"})
	public String orderHist(@PathVariable("page") Optional<Integer>page, Principal principal, Model model) {
		Pageable pageable =  PageRequest.of(page.isPresent() ? page.get() : 0, 4);
		
		Page<OrderHistDto> orderHistDto = orderService.getOrderList(principal.getName(), pageable);
		
		model.addAttribute("orders", orderHistDto);
		for(OrderHistDto orderHist : orderHistDto) {
			System.out.println(orderHist.getOrderItemDtoList() + "으아아아ㅏ아아아아아아ㅏ아아ㅏㄱ");
		}
		
		model.addAttribute("maxPage", 5);
		model.addAttribute("page", pageable.getPageNumber());
		
		return "/item/orderHist";
	}


	
}
