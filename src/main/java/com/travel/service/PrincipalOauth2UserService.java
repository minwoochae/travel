package com.travel.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.travel.Repository.MemberRepository;
import com.travel.auth.KakaoMemberInfo;
import com.travel.auth.OAuth2UserInfo;
import com.travel.auth.PrincipalDetails;
import com.travel.constant.Division;
import com.travel.constant.Role;
import com.travel.entity.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	private final MemberRepository memberRepository;
    
    @Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
	 OAuth2User oAuth2User = super.loadUser(userRequest);
	 OAuth2UserInfo oAuth2UserInfo = null;
     String provider = userRequest.getClientRegistration().getRegistrationId();    
     oAuth2UserInfo = new KakaoMemberInfo(oAuth2User.getAttributes());
     
     String providerId = oAuth2UserInfo.getProviderId();
     String username = oAuth2UserInfo.getName(); 
     String password = "SNS 로그인";  // 사용자가 입력한 적은 없지만 만들어준다
	
     String email = oAuth2UserInfo.getEmail();
 
     Division division = Division.KAKAO; 
     
     	
     Member member = memberRepository.findByEmail(email);
     if(member == null){
    	 member = Member.oauth2Register()
        		 .email(email).name(username).password(password)
                 .provider(provider).providerId(providerId).division(division)
                 .build();
     }
     
     
     
     
     return new PrincipalDetails(member, oAuth2UserInfo);
}}
