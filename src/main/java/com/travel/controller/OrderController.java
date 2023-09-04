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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.BeforeOrderDto;
import com.travel.Dto.CartDto;
import com.travel.Dto.CartItemDto;
import com.travel.Dto.OrderHistDto;
import com.travel.Dto.OrderItemDto;
import com.travel.auth.PrincipalDetails;
import com.travel.entity.Member;
import com.travel.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	
	//상품 바로 주문하기
	@PostMapping("/orderItem")
	public @ResponseBody ResponseEntity orderItem(
	    @RequestParam("itemId") Long itemId,
	    @RequestParam("count") int count
	) {
		System.out.println(itemId);
		System.out.println(count);
	    String redirectUrl = "/orderItem/address?itemId=" + itemId + "&count=" + count;
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Location", redirectUrl);
	    return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
    
    @GetMapping("/orderItem/address")
    public String orderItemOneAddress(@RequestParam("itemId") Long itemId, @RequestParam("count") int count, Model model) {
        System.out.println("여긴옴??");
    	// 받아온 itemId와 count를 사용하여 필요한 로직을 수행
        // 예를 들어, 주문 상품 정보를 조회하거나 다른 처리를 수행할 수 있습니다.

        // 아래는 예시로 모델에 데이터를 추가하는 부분입니다.
        // 실제 필요한 데이터를 모델에 추가하세요.
        model.addAttribute("itemId", itemId);
        model.addAttribute("count", count);

        return "item/address2"; // 주소가 맞는지 확인하세요
    }

	//장바구니에서 주문하기
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
		System.out.println(principal.getName());
		Page<OrderHistDto> orderHistDto = orderService.getOrderList(principal.getName(), pageable);
		
		model.addAttribute("orders", orderHistDto);
		model.addAttribute("maxPage", 5);
		model.addAttribute("page", pageable.getPageNumber());
		
		return "/item/orderHist";
	}


	

	
}
