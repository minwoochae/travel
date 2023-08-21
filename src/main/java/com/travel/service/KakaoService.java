package com.travel.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import org.json.simple.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.travel.Dto.MemberKakaoDto;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoService {


		public String getToken(String code) throws IOException, ParseException {
	        // 인가코드로 토큰받기
	        String host = "https://kauth.kakao.com/oauth/token";
	        URL url = new URL(host);
	        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	        String token = "";
	        try {
	            urlConnection.setRequestMethod("POST");
	            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=98f390fb8a9c8c0429606de4259a885c");
	            sb.append("&redirect_uri=http://localhost/members/login/kakao");
	            sb.append("&code=" + code);

	            bw.write(sb.toString());
	            bw.flush();

	            int responseCode = urlConnection.getResponseCode();
	            System.out.println("responseCode 확인 = " + responseCode);

	            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	            String line = "";
	            String result = "";
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("result = " + result);

	            // json parsing
	            JSONParser parser = new JSONParser(result);
	            JSONObject elem = (JSONObject) parser.parse();

	            String access_token = elem.get("access_token").toString();
	            String refresh_token = elem.get("refresh_token").toString();
	            System.out.println("refresh_token = " + refresh_token);
	            System.out.println("access_token = " + access_token);

	            token = access_token;

	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }


	        return token;
	    }

	    // 유저 정보 받아오기
		public MemberKakaoDto getUserInfo(String access_token) throws IOException, ParseException {
		    String host = "https://kapi.kakao.com/v2/user/me";
		    MemberKakaoDto memberKakaoDto = new MemberKakaoDto();

		    try {
		        URL url = new URL(host);
		        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		        urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
		        urlConnection.setRequestMethod("GET");

		        int responseCode = urlConnection.getResponseCode();
		        System.out.println("responseCode = " + responseCode);

		        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		        String line = "";
		        String res = "";
		        while ((line = br.readLine()) != null) {
		            res += line;
		        }

		        System.out.println("res = " + res);

		        JSONParser parser = new JSONParser(res);
		        JSONObject obj = (JSONObject) parser.parse();
		        JSONObject kakao_account = (JSONObject) obj.get("kakao_account");

		        String id = obj.get("id").toString();
		        String email = kakao_account.get("email").toString();

		        memberKakaoDto.setId(id);
		        memberKakaoDto.setEmail(email);

		        br.close();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return memberKakaoDto;
		}
		
		
	    // 정상적으로 받아왔는지 여부
	    public String getAgreementInfo(String access_token)
	    {
	        String result = "";
	        String host = "https://kapi.kakao.com/v2/user/scopes";
	        try{
	            URL url = new URL(host);
	            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
	            urlConnection.setRequestMethod("GET");
	            urlConnection.setRequestProperty("Authorization", "Bearer "+access_token);

	            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	            String line = "";
	            while((line=br.readLine())!=null)
	            {
	                result+=line;
	            }

	            int responseCode = urlConnection.getResponseCode();
	            System.out.println("responseCode = " + responseCode);

	            // result is json format
	            br.close();

	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (ProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }

	    public String getAccessToken (String authorize_code) {
	        String access_Token = "";
	        String refresh_Token = "";
	        String reqURL = "http://localhost/members/login/kakao";
	 
	        try {
	            URL url = new URL(reqURL);
	 
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	 
	            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로 변경을 해주세요
	 
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	 
	            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	// BufferedWriter 간단하게 파일을 끊어서 보내기로 토큰값을 받아오기위해 전송

	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("grant_type=authorization_code");
	            sb.append("&client_id=98f390fb8a9c8c0429606de4259a885c");  //본인이 발급받은 key
	            sb.append("&redirect_uri=http://localhost/members/login/kakao");     // 본인이 설정해 놓은 경로
	            sb.append("&code=" + authorize_code);
	            bw.write(sb.toString());
	            bw.flush();
	 
	            //    결과 코드가 200이라면 성공
	// 여기서 안되는경우가 많이 있어서 필수 확인 !! ** 
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode+"확인");
	 
	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	 
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result+"결과");
	 
				/*
				 * // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성 JsonParser parser = new JsonParser();
				 * JsonElement element = parser.parse(result);
				 * 
				 * 
				 * 
				 * access_Token = element.getAsJsonObject().get("access_token").getAsString();
				 * refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
				 * 
				 * System.out.println("access_token : " + access_Token);
				 * System.out.println("refresh_token : " + refresh_Token);
				 * 
				 * br.close(); bw.close();
				 */
	            
	            String jsonString = "{\"key\":\"value\"}";

	            JsonElement jsonElement = JsonParser.parseString(jsonString);
	            JsonObject jsonObject = jsonElement.getAsJsonObject();

	            String value = jsonObject.get("key").getAsString();
	            System.out.println("Value: " + value);
	            
	            JsonElement element = JsonParser.parseString(result);
	            
	            access_Token = element.getAsJsonObject().get("access_token").getAsString();
	            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

	            System.out.println("access_token : " + access_Token);
	            System.out.println("refresh_token : " + refresh_Token);
	            br.close(); bw.close();
	        } catch (IOException e) {
	 
	            e.printStackTrace();
	        }
	 
	        return access_Token;
	    }
		
	    
	}

