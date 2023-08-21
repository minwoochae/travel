package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.travel.entity.Tourist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourFormDto {

	private Long id;
	
	private String touristTitle;
	
	private String touristContent;
	
	private List<TourImgDto> tourImgDtoList = new ArrayList<>();
	
	private List<Long> tourImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto -> entity
	public Tourist createTour() {
		return modelMapper.map(this, Tourist.class);
	}
	
	// entity -> dto
	public static TourFormDto of(Tourist tourist) {
		return modelMapper.map(tourist, TourFormDto.class);
	}
}
