package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import com.travel.entity.PlanContent;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanFormDto {
	
	@NotBlank(message = "플랜 이름은 필수입니다.")
	private String planTitle;
	
	@NotBlank(message = "플랜 날짜는 필수입니다.")
	private String planDate;
	
	
	private List<PlanContent> planContentDtoList = new ArrayList<>();
	
}
