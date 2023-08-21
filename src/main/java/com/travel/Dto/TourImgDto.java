package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.entity.TouristImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TourImgDto {

	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper =  new ModelMapper();
	
	public static TourImgDto of(TouristImg tourImg) {
		return modelMapper.map(tourImg, TourImgDto.class);
	}
}
