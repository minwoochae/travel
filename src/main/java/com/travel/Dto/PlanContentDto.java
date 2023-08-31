package com.travel.Dto;

import com.travel.constant.TravelDivision;
import com.travel.entity.Plan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanContentDto {

	
	private String planDay;
	
	private String travelDivision;
	
	private String placeName;
	
	private String placeAddress;
	
	private String placeTel;
	
	private String placeLatitude;
	
	private String placeLongitude;
	
	private String place_img;
	
	private int area_code;
	
	private int sigungu_code;
	
	private int content_id;
	
	private int content_type;
	
	private Plan plan;
}
