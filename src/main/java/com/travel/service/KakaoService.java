package com.travel.service;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.Repository.MemberRepository;
import com.travel.entity.Member;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
@Transactional
	
public class KakaoService {

    private final MemberRepository memberRepository;


    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    //이메일 검사후 저장 
    public void saveMember(Member member) {
//        insertUserInfoToDatabase(member);

        // 기존에 가입되어 있는 고객인지 확인
        Member searchMember = memberRepository.findByEmail(member.getEmail());
        // 가입이 안되어 있다면 회원가입 시작.
        if (searchMember == null){
            memberRepository.save(member);
        }else {
			 throw new IllegalStateException("이미 사용중인 Email 입니다"); 
        }
    }
    
    

	 
	}