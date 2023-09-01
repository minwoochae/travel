package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.entity.Member;
import com.travel.entity.Plan;
import com.travel.entity.PlanCommunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanCommunityDto {
	private Long id;
	
	private String communityTitle;
	
	private String communityContent;
	
	private String communityRegDate; 
	
	private Member member;
	
	private Plan plan;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public PlanCommunity createPlanCommunity(String regDate, Member member, Plan plan) {
		this.communityRegDate = regDate;
		this.member = member;
		this.plan = plan;
		return modelMapper.map(this, PlanCommunity.class);
	}
	
	public static PlanCommunityDto of(PlanCommunity planCommunity) {
		return modelMapper.map(planCommunity, PlanCommunityDto.class);
	}
}
