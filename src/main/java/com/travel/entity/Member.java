package com.travel.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travel.Dto.MemberFormDto;
import com.travel.Dto.MemberKakaoDto;
import com.travel.constant.Division;
import com.travel.constant.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Member extends BaseEntity {

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 멤버 아이디

	@Column(unique = true, length = 255)
	private String email; // 이메일

	@Column(unique = false, length = 255)
	private String name; // 이름

	@Column(nullable = false, length = 255)
	private String password; // 비밀번호

	@Column(nullable = true)
	private String phoneNumber; // 폰번호


	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<Plan> plans = new ArrayList<>();
	

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<PlanCommunity> plancommunity = new ArrayList<>();


	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime regtime;

	@Enumerated(EnumType.STRING)
	private Role role; // 역할

	@Enumerated(EnumType.STRING)
	private Division division; // 역할

	private String provider;

	private String providerId;


	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(memberFormDto.getPassword());

		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPhoneNumber(memberFormDto.getPhoneNumber());
		member.setPassword(password);
		member.setRole(Role.ROLE_USER);
		member.setRegtime(memberFormDto.getRegtime());
		member.setDivision(Division.NORMAL);
		return member;

	}

	public void updateMember(MemberFormDto memberFormDto) {
		this.password = memberFormDto.getPassword();
	}

	public String updatePassword(String pass, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(pass);
		this.password = password;

		return password;
	}

	public void updatenamePhone(String name, String phone) {
		this.name = name;
		this.phoneNumber = phone;
	}

	public void updatepassword(String password) {
		this.password = password;
	}

	public static Member createKaKao(MemberKakaoDto memberKakaoDto, PasswordEncoder passwordEncoder) {

		Member member = new Member();
		member.setProvider(memberKakaoDto.getProvider());
		member.setProviderId(memberKakaoDto.getProviderId());
		member.setEmail(memberKakaoDto.getEmail());
		member.setPassword(memberKakaoDto.getPassword());
		member.setName(memberKakaoDto.getName());
		member.setPhoneNumber(memberKakaoDto.getPhoneNumber());
		member.setRole(Role.ROLE_USER);
		member.setDivision(Division.KAKAO);

		return member;

	}


	// 정보 저장
	public void kakaoinsert(HashMap<String, Object> userInfo) {
		this.kakaoinsert(userInfo);
	}

	public Member(String name, String email) {
		this.name = name;
		this.email = email;
	}

	@Builder(builderClassName = "MemberDetailRegister", builderMethodName = "MemberDetailRegister")
	public Member(String email, String password, String name, Role role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.role = role;
	}

	@Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
	public Member(String email, String password, String name,  String provider, String providerId, Division division) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.provider = provider;
		this.providerId = providerId;
		this.division = division;
	}

}
