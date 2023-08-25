package com.travel.Dto;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.travel.entity.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class MemberKakaoDto {
	private String id;
	


@NotBlank(message = "이름은 필수 입력 값입니다.")
private String name;


@NotBlank(message = "이메일은 필수 입력 값입니다.")
@Email(message = "이메일 형식으로 입력해주세요.")
private String email;


@NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
@Length(min = 8 , max = 16 , message = "비밀번호 8자~ 16자 사이로 입력해주세요.")
private String password;


@NotBlank(message = "전화번호는 필수 입력 값 입니다.")
@Pattern(regexp = "^\\d{3}\\d{3,4}\\d{4}$", message = "유효한 전화번호 형식을 입력해주세요.") 
private String phoneNumber;

private LocalDateTime regtime;


private String division;


private String role;

private static ModelMapper modelMapper = new ModelMapper();
	
	public Member createMember() {
		return modelMapper.map(this , Member.class);
	}

	//entity -> dto로 바꿈
	public static MemberKakaoDto of(Member member) {
		return modelMapper.map(member, MemberKakaoDto.class);
	}
}
