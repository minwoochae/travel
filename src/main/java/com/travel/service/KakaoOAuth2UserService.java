package com.travel.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 카카오로부터 받은 사용자 정보를 처리하고 반환하는 로직 구현
        // 예시로 간단한 사용자 정보를 생성하여 반환하겠습니다.
        
        Map<String, Object> userAttributes = new HashMap<>();
        // userAttributes에 카카오로부터 받은 사용자 정보를 매핑
        
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(
                new OAuth2UserAuthority(userAttributes)
        );

        return new DefaultOAuth2User(authorities, userAttributes, "id"); // 예시로 "id"를 기본 이름으로 설정
    }
}