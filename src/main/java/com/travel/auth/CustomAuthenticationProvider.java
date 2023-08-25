package com.travel.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.travel.config.SecurityConfig;
import com.travel.entity.Member;
import com.travel.service.MemberService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	 
	
	  private final MemberService memberService;
	    private final PasswordEncoder passwordEncoder;
	    private final SecurityConfig securityConfig;

	    
	    @Autowired
	    private KakaoAuthenticationSuccessHandler kakaoAuthenticationSuccessHandlers;

	    @Autowired
	    private CustomAuthenticationProvider customAuthenticationProvider;

	    @Bean
	    public AuthenticationManager authenticationManager() {
	        return new ProviderManager(Collections.singletonList(customAuthenticationProvider));
	    }
	    

	    @Autowired
	    public CustomAuthenticationProvider(MemberService memberService, PasswordEncoder passwordEncoder,
	                                       SecurityConfig securityConfig, 
	                                       @Qualifier("kakaoAuthenticationSuccessHandler")
	                                       KakaoAuthenticationSuccessHandler kakaoAuthenticationSuccessHandlers) {
	        this.memberService = memberService;
	        this.passwordEncoder = passwordEncoder;
	        this.securityConfig = securityConfig;
	        this.kakaoAuthenticationSuccessHandlers = kakaoAuthenticationSuccessHandlers;
	    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();

        Member member = memberService.findByEmail(email);

        if (member != null) {
            return new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
        }

        return null; // 인증 실패
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}