package com.travel.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travel.Repository.MemberRepository;
import com.travel.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@Transactional //퀘리문 오류시 이전데이터 롤백
@RequiredArgsConstructor  // autowired를 사용하지 않고 필드의 의존성을 주입 시켜준다
public class MemberService implements UserDetailsService{
	
	private final MemberRepository memberRepository ;
	
	
	public String emailFind(String name, String phone) {
		Member member = memberRepository.findByNameAndPhoneNumber(name, phone);

		if (member == null) {
			return "일치하는 사용자가 없습니다";
		}
		return member.getEmail();
	}

}
