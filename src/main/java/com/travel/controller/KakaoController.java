package com.travel.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.travel.Dto.MemberKakaoDto;
import com.travel.auth.PrincipalDetails;
import com.travel.entity.Member;
import com.travel.service.IKakaoLoginService;
import com.travel.service.KakaoService;
import com.travel.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	@Autowired
	public IKakaoLoginService iKakaoS;


	@Autowired
	public KakaoService kakaoService;
	@Autowired
	public MemberService memberService;
	@Autowired
	public  PasswordEncoder  passwordEncoder;

	//카카오 회원가입
	@GetMapping(value = "/oauth2/code/kakao")
	public String memberForm(Authentication authentication, Model model) throws Throwable{
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        Member member = principal.getMember();
		
		
		MemberKakaoDto memberKakaoDto = new MemberKakaoDto();
		memberKakaoDto.setEmail(member.getEmail());
		memberKakaoDto.setName(member.getName());
		memberKakaoDto.setPassword("SNS 비밀번호");
		model.addAttribute("memberKakaoDto", memberKakaoDto);

    	
		return "member/KakaoMemberForm";
		
	}
	
	//카카오 회원가입
	@PostMapping(value = "/oauth2/code/kakao")
	public String memberForm(@Valid MemberKakaoDto memberKakaoDto, BindingResult bindingResult, Model model)
	{
		if (bindingResult.hasErrors()) {
			
			return "member/KakaoMemberForm";
		}

		try {
			Member member = Member.createKaKao(memberKakaoDto, passwordEncoder);
			kakaoService.saveMember(member);
		

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/KakaoMemberForm";
		}

		return "redirect:/";
	}


	
}