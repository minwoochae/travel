package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.travel.entity.Plan;
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
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public Plan createPlan() {
		return modelMapper.map(this, Plan.class);
	}
	
	public static PlanFormDto of(Plan plan) {
		return modelMapper.map(plan, PlanFormDto.class);
	}
}
