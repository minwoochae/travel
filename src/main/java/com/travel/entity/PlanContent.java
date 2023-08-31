package com.travel.entity;

import com.travel.Dto.PlanContentDto;
import com.travel.constant.TravelDivision;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="plan_content") 
@Getter
@Setter
@ToString
public class PlanContent {

	@Id
	@Column(name="plan_content_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name="plan_day")
	private String planDay;
	
	private String travelDivision;
	
	@Column(name="place_name")
	private String placeName;
	
	@Column(name="place_tel")
	private String placeTel;
	
	@Column(name="place_address")
	private String placeAddress;
	
	@Column(name="place_latitude")
	private String placeLatitude;
	
	@Column(name="place_longitude")
	private String placeLongitude;
	
	@Column(name="place_img")
	private String place_img;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;
	
	
	public static PlanContent createContent(PlanContentDto planContentDto, Plan plan) {
		PlanContent planContent = new PlanContent();
		planContent.setPlanDay(planContentDto.getPlanDay());
		planContent.setTravelDivision(planContentDto.getTravelDivision());
		planContent.setPlaceName(planContentDto.getPlaceName());
		planContent.setPlaceAddress(planContentDto.getPlaceAddress());
		planContent.setPlaceLatitude(planContentDto.getPlaceLatitude());
		planContent.setPlaceLongitude(planContentDto.getPlaceLongitude());
		planContent.setPlace_img(planContentDto.getPlace_img());
		planContent.setPlan(plan);
		return planContent;
	}
	
	
}
