package com.travel.Dto;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;

import com.travel.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MemberKakaoDto {
	private String id;
	
private static ModelMapper modelMapper = new ModelMapper();

@NotBlank(message = "이름은 필수 입력 값입니다.")
private String name;


@NotBlank(message = "이메일은 필수 입력 값입니다.")
@Email(message = "이메일 형식으로 입력해주세요.")
private String email;


private LocalDateTime regtime;


private String division;


private String role;

	
	public Member createMember() {
		return modelMapper.map(this , Member.class);
	}

	//entity -> dto로 바꿈
	public static MemberKakaoDto of(Member member) {
		return modelMapper.map(member, MemberKakaoDto.class);
	}
}
