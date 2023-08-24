package com.travel.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.travel.Dto.MemberFormDto;
import com.travel.Dto.MemberKakaoDto;
import com.travel.constant.Division;
import com.travel.constant.Role;
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
	public ModelAndView kakaoLogin(@RequestParam(value = "code", required = false) String code , Model model) throws Throwable {

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
		// memberKakaoDto.setEmail(email);
		// memberKakaoDto.setName(name);
		//String ramdomps = memberService.getRamdomPassword(12);
		
		MemberKakaoDto memberKakaoDto = new MemberKakaoDto();
		memberKakaoDto.setEmail(email);
		memberKakaoDto.setName(name);
		//memberKakaoDto.setPassword(ramdomps);

        String errorMessage = "가입이 되어 있는 카카오 계정입니다.";
        model.addAttribute("errorMessage", errorMessage);
		
		/* Member member = Member.createKaKao(memberKakaoDto ,passwordEncoder); */

		
		ModelAndView modelAndView = new ModelAndView("/kakao/new");
		return modelAndView;
	}
	
	//카카오  회원가입 화면
	@GetMapping(value = "/kakao/new")
	public String memberForme(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "member/KakaoMemberForm";
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
