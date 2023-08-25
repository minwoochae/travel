package com.travel.auth;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class KakaoAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {

	 @Override
	    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                        Authentication authentication) throws IOException {
	        DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
	        Map<String, Object> attributes = oidcUser.getAttributes();
	        String email = (String) attributes.get("email"); // 카카오로부터 이메일 정보 추출

	        // 이메일 정보를 기반으로 회원가입 및 로그인 처리 수행
	        // 예시로 로그인 처리를 보여주겠습니다.
	        // 실제로는 회원가입 및 로그인을 위한 서비스와 처리 로직을 작성해야 합니다.
	        // 이 예시에서는 세션에 사용자 정보를 저장하는 것으로 간단하게 로그인 처리를 하겠습니다.
	        // 실제로는 Spring Security의 인증 매커니즘을 활용하시길 권장합니다.
	        
	        // 사용자 정보를 이용하여 Spring Security의 인증 매커니즘을 활용한 로그인 처리를 수행하거나
	        // 서비스를 호출하여 회원가입 및 로그인 처리를 수행할 수 있습니다.
	        // 예시로 세션에 사용자 정보를 저장하는 코드를 작성하겠습니다.
	        // 실제로는 Spring Security의 인증 매커니즘을 활용하여 로그인 처리하는 것이 더 안전하고 효율적입니다.
	        HttpSession session = request.getSession();
	        session.setAttribute("loggedInUserEmail", email); // 세션에 사용자 이메일 저장
	        
	        response.sendRedirect("/"); // 로그인 후 홈페이지로 리다이렉트
	    }


}
