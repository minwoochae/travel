package com.travel.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.data.domain.*;
import com.travel.Dto.MemberFormDto;
import com.travel.entity.Member;
import com.travel.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberservice;
	private final PasswordEncoder passwordEncoder;
	
	//로그인 화면
	@GetMapping(value = "/members/login")
	public String loginmember() {
		return "member/memberLoginForm";
	}

	
	//회원가입 화면
	@GetMapping(value = "/members/new")
	public String memberForme(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/memberForm";
	}
	//회원가입
	@PostMapping(value = "/members/new")
	public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "member/memberForm";
		}
		
		try {
			Member member = Member.createMember(memberFormDto, passwordEncoder);
				
			
			memberservice.saveMember(member);
		} catch(IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/memberForm";
		}
		
		return "redirect:/";
	}
	
	
	@GetMapping(value = "/members/login/error")
	public String loginError(Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
		
		return "member/memberLoginForm";
	}
	
	
	
	// 아이디 찾기
	@GetMapping(value = "/account/search")
	public String search_id(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/LoginForm";
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
	
	// 비번찾기
		@GetMapping(value = "/account/pssearch")
		public String search_ps(Model model) {
			model.addAttribute("memberFormDto", new MemberFormDto());

			return "member/psLoginForm";
		}

		// 비밀번호 찾고 난수생성기로 랜덤비밀번호 생성
		@PostMapping("/account/pssearch")
		@ResponseBody
		public HashMap<String, String> memberps(@RequestBody Map<String, Object> psdata , Principal principal) {
			String email = (String) psdata.get("memberEmail");

			HashMap<String, String> msg = new HashMap<>();
			String pass = memberservice.passwordFind( email);
			// pass 암호화된 비밀번호
			String ramdomps = memberservice.getRamdomPassword(12);

			// ramdomps 를 view에 출력
			String password = memberservice.updatePassword(ramdomps, email, passwordEncoder);
			
			/* memberservice.sendEmail(email, "새로운 비밀번호", "새로운 비밀번호: " + ramdomps); */
			String asd = "이메일로 임시 비밀번호가 발송되었습니다.";
			msg.put("message", asd);
			return msg ;
		}
	
		//mypage
		 @GetMapping(value = "/member/mypage")
		 public String mainMypage (Model model) {
			 
			 return "member/MyPage";
		 }
		
		 

	
}