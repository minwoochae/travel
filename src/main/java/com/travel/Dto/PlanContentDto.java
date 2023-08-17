package com.travel.Dto;

import com.travel.constant.TravelDivision;
import com.travel.entity.Plan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanContentDto {

	private Long id;
	
	private String planDay;
	
	private TravelDivision travelDivision;
	
	private String placeName;
	
	private String placeAddress;
	
	private String placeLatitude;
	
	private String placeLongitude;
	
	private String place_img;
	
	private Plan plan;
}
