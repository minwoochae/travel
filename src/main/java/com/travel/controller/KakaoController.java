package com.travel.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.Dto.MemberKakaoDto;
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
	@RequestMapping(value = "/members/login/kakao", method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code , Model model) throws Throwable {

		// 1번
		System.out.println("code:" + code);

		// 2번
		String access_Token = iKakaoS.getAccessToken(code);
		System.out.println("###access_Token#### : " + access_Token);
		// 위의 access_Token 받는 걸 확인한 후에 밑에 진행

		// 3번
		HashMap<String, Object> userInfo = iKakaoS.getUserInfo(access_Token);
		System.out.println("###nickname#### : " + userInfo.get("nickname"));
		System.out.println("###email#### : " + userInfo.get("email"));

		String name = (String) userInfo.get("nickname");
		String email = (String) userInfo.get("email");

		
		MemberKakaoDto memberKakaoDto = new MemberKakaoDto();
		memberKakaoDto.setEmail(email);
		memberKakaoDto.setName(name);
		memberKakaoDto.setPassword("SNS 비밀번호ㅇㅅㅇ");
		System.out.println(memberKakaoDto.getEmail());

   
		Member existingMember = memberService.findByEmail(email);
        if (existingMember == null) {
        	
        	String errorMessage = "가입이 되어 있지 않은 카카오 계정입니다.";
        	model.addAttribute("errorMessage", errorMessage);
        	model.addAttribute("memberKakaoDto" ,memberKakaoDto);
     
 
        	return "member/KakaoMemberForm";
        }
        
    	
    	
    	
    	
    	
		return "redirect:/";
		
		
	}
	

	
	//카카오 회원가입
	@PostMapping(value = "/kakao/new")
	public String memberForm(@Valid MemberKakaoDto memberKakaoDto, BindingResult bindingResult, Model model) {
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