package com.travel.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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


	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// 사용자가 입력한 email이 DB에 있는지 쿼리문을 사용한다.
		Member member = memberRepository.findByEmail(email);

		if (member == null) {
			throw new UsernameNotFoundException(email);
		}

		// 사용자가 있다면 DB에서 가져온 값으로 userDetails 객체를 만들어서 반환
		return User.builder().username(member.getEmail()).password(member.getPassword())
				.roles(member.getRole().toString()).build();
	}

}
