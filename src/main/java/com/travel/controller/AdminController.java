package com.travel.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.travel.Dto.InfoFormDto;
import com.travel.Dto.ItemFormDto;
import com.travel.Dto.ItemSearchDto;
import com.travel.Dto.MemberFormDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.travel.entity.Item;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.MemberFormDto;

import com.travel.entity.Member;
import com.travel.service.ItemService;
import com.travel.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class AdminController {

	private final ItemService itemService;

	private final MemberService memberService;

	@GetMapping(value = "/admin")
	public String admin() {
		return "/admin/adminMain";
	}
	
	


	// 회원 리스트

	@GetMapping(value = { "/admin/list", "/admin/list/{page}" })
	public String memberManage(@PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

		Page<Member> travel = memberService.getAdminlistPage(pageable);
		model.addAttribute("travel", travel);
		model.addAttribute("maxPage", 5);

		return "admin/MemberList";
	}

		
	// 쇼핑몰 상품 리스트
	@GetMapping(value = { "/adminShop", "/adminShop/{page}" })
	public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

		// of(조회할 페이지의 번호★[0부터 시작], 한페이지당 조회할 데이터 갯수)
		// url 경로에 페이지가 있으면 해당 페이지 번호를 조회하도록 하고 페이지 번호가 없으면 0페이지를 조회.
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

		Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

		model.addAttribute("items", items);
		model.addAttribute("itemSearchDto", itemSearchDto);
		model.addAttribute("maxPage", 5); // 상품관리페이지 하단에 보여줄 최대 페이지 번호

		return "/admin/itemList";

	}

		//회원 리스트(회원 프로필)

		@GetMapping(value =  {"/admin/profile" , "/admin/profile/{memberId}"})
		public String  Profilemember(@PathVariable("memberId") Long memberId, Model model) {
				MemberFormDto memberFormDto = memberService.getmemberDtl(memberId);
				
				model.addAttribute("member", memberFormDto);
			return "admin/profile";
		}



	// 쇼핑몰 상품 등록하기
	@GetMapping(value = "/adminShop/new")
	public String adminShop(Model model) {
		model.addAttribute("itemFormDto", new ItemFormDto());
		return "/admin/itemRegist";
	}

	// 상품, 상품이미지 등록
	@PostMapping(value = "/adminShop/new")
	public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
			@RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {

		if (bindingResult.hasErrors()) {
			return "/admin/itemRegist";
		}

		// 상품등록전에 첫번째 이미지가 있는지 없는지 검사.(첫번째 이미지는 필수입력값)
		if (itemImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입니다.");
			return "/admin/itemRegist";
		}

		try {
			itemService.saveItem(itemFormDto, itemImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "상품 등록 중 에러가 발생했습니다.");
			return "/admin/itemRegist";
		}

		return "redirect:/";
	}

	
	// 상품 수정페이지 보여주기
	@GetMapping(value = "/adminShop/item/{itemId}")
	public String itemDtl(@PathVariable("itemId") Long itemId,Model model) {
		
		try {
			ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
			model.addAttribute("itemFormDto",itemFormDto);
		} catch (Exception e) {
			e.printStackTrace(); //이거 안하면 에러 안찍힘.
			model.addAttribute("errorMessage", "상품정보를 가져올 때 에러가 발생했습니다.");
			//에러발생시 비어있는 객체를 넘겨준다. 
			model.addAttribute("itemFormDto", new ItemFormDto()); 
			return "admin/itemRegist";
		}
		
		
		return "admin/itemModify";
	}
	
	//상품수정
	@PostMapping(value = "/adminShop/item/{itemId}")
		public String itemUpdate(@Valid ItemFormDto itemFormDto,Model model,
				BindingResult bindingResult, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) {
			
			if(bindingResult.hasErrors()) {
				return "admin/itemRegist";
			}
			
			//첫번째 이미지가 있는지 검사. 
			if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId()==null) {
				model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수입니다.");
				return "admin/itemRegist";
			}
			
			try {
				itemService.updateItem(itemFormDto, itemImgFileList);
				
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "상품 수정 중 에러가 발생했습니다.");
				return "admin/itemRegist";
			}
			
			return "redirect:/";
		}
	
	
	// 상품 삭제
	@DeleteMapping("/adminShop/{itemId}/delete")
	public @ResponseBody ResponseEntity deletemovie(@RequestBody @PathVariable("itemId") Long itemId,
			Principal principal) {
		itemService.deleteItem(itemId);
		return new ResponseEntity<Long>(itemId, HttpStatus.OK);
	}
	
	
	// 공지사항 리스트

		@GetMapping(value = { "/adminInfo", "/adminInfo/{page}" })
		public String infoManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {

			// of(조회할 페이지의 번호★[0부터 시작], 한페이지당 조회할 데이터 갯수)
			// url 경로에 페이지가 있으면 해당 페이지 번호를 조회하도록 하고 페이지 번호가 없으면 0페이지를 조회.
			Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);

			Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable);

			//model.addAttribute("infoBoards", infoBoards);
			model.addAttribute("itemSearchDto", itemSearchDto);
			model.addAttribute("maxPage", 5); // 상품관리페이지 하단에 보여줄 최대 페이지 번호

			return "/admin/infoList";

		}
	
	
	// 공지사항 등록 보여주기
	@GetMapping(value = "/adminInfo/new")
		public String adminInfo(Model model) {
			model.addAttribute("infoFormDto", new InfoFormDto());
			return "/admin/infoForm";
		}

	// 공지사항, 공지사항이미지 등록
	@PostMapping(value = "/adminInfo/new")
		public String InfoNew(@Valid InfoFormDto infoFormDto, BindingResult bindingResult, Model model) {

			

			return "redirect:/";
		}
	

	
	// 회원 탈퇴시키기
	@DeleteMapping(value = "admin/{memberId}/delete")
	public @ResponseBody ResponseEntity deleteMember(@RequestBody @PathVariable("memberId") Long memberId,
			Principal principal) {

		memberService.deleteMember(memberId);

		return new ResponseEntity<Long>(memberId, HttpStatus.OK);
	}

}
	 
		


