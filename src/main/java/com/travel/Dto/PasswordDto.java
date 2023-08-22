package com.travel.Dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
	
	@NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
	@Length(min = 8 , max = 16 , message = "비밀번호 8자~ 16자 사이로 입력해주세요.")
	private String password;
	
}
