package com.travel.controller;

import java.io.IOException;
import java.util.HashMap;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.travel.Dto.MemberKakaoDto;
import com.travel.constant.Division;
import com.travel.constant.Role;
import com.travel.entity.Member;
import com.travel.service.IKakaoLoginService;
import com.travel.service.KakaoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	@Autowired
	public IKakaoLoginService iKakaoS;
	
	
	public KakaoService kakaoService;
	
	@RequestMapping(value = "/members/login/kakao", method = RequestMethod.GET)
	public ModelAndView kakaoLogin(@RequestParam(value = "code", required = false) String code ,@Valid MemberKakaoDto memberKakaoDto) throws Throwable {

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
        
        
        Member member = Member.createKaKao(memberKakaoDto);
        
        member.setEmail(email);
        member.setName(name);
        member.setRole(Role.USER);
        member.setRegtime(member.getRegtime());
        member.setDivision(Division.NORMAL);
        
            kakaoService.saveMember(member);
        

        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
	}
	
	


	

}
