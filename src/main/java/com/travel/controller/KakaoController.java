package com.travel.controller;

import java.io.IOException;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.Dto.MemberKakaoDto;
import com.travel.service.KakaoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class KakaoController {
	private final KakaoService kakaoService;

	@GetMapping("/members/login/kakao")
	public void kakaoLogin(@RequestParam(value = "code", required = false) String code, HttpSession session)
			throws IOException, ParseException {
		System.out.println("####### " + code);

		String access_Token = kakaoService.getAccessToken(code);
		MemberKakaoDto userInfo = kakaoService.getUserInfo(access_Token);

		System.out.println("###access_Token#### : " + access_Token);
		System.out.println("###nickname#### : " + userInfo.getName());
		System.out.println("###email#### : " + userInfo.getEmail());
	}
}
