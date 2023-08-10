package com.travel.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.Dto.MemberFormDto;
import com.travel.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberservice ;
	
	//로그인 화면
	@GetMapping(value = "/members/login")
	public String loginmember() {
		return "members/memberLoginForm";
	}
	
	//회원가입 화면
	@GetMapping(value = "/members/new")
	public String memberForme(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "members/memberForm";
	}
	
	// 아이디 찾기
	@GetMapping(value = "/account/search")
	public String search_id(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "members/LoginForm";
	}

	@PostMapping("/account/search")
	@ResponseBody
	public HashMap<String, String> members(@RequestBody Map<String, Object> data) {
		String name = (String) data.get("memberName");
		String phone = (String) data.get("memberPhoneNumber");
		/*
		 * Member foundMember = memberRepository.findByNameAndPhoneNumber( name ,
		 * phoneNumber);
		 */
		HashMap<String, String> msg = new HashMap<>();
		String email = memberservice.emailFind(name, phone);

		msg.put("message", email);
		return msg;
	}
	
}
