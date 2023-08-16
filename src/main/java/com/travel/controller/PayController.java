package com.travel.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PayController {
	
	@GetMapping("/kakao")
	@ResponseBody
	public String kakaopay() {
		try {
			URL kakaoUrl = new URL("https://kapi.kakao.com/v1/payment/ready");
			try {
				HttpURLConnection conServer = (HttpURLConnection) kakaoUrl.openConnection();
				conServer.setRequestMethod("POST");
				conServer.setRequestProperty("Authorization", "KakaoAK ${74456afa519cdeabeb17c1bbaf21a4c1}");
				conServer.addRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
				conServer.setDoOutput(true);
				String param = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&item_name=초코파이&quantity=1&total_amount=2200&tax_free_amount=0&approval_url=https://developers.kakao.com/success&cancel_url=https://developers.kakao.com/cancel&fail_url=https://developers.kakao.com/fail";
				OutputStream outPut = conServer.getOutputStream();
				DataOutputStream dataOutPut = new DataOutputStream(outPut);
				dataOutPut.writeBytes(param);
				dataOutPut.close();
				
				int result = conServer.getResponseCode();
				System.out.println(result);
				
				InputStream dataInput;
				if(result == 200) {
					dataInput = conServer.getInputStream();
				} else {
					dataInput = conServer.getErrorStream();
				}
				
				InputStreamReader reader = new InputStreamReader(dataInput);
				BufferedReader buffer = new BufferedReader(reader);
				return buffer.readLine();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return "Item/kakao";
	}
}
