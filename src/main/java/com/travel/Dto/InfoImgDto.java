package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.entity.InfoBoardImg;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoImgDto {

	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static InfoImgDto of(InfoBoardImg infoBoardImg) {
		return modelMapper.map(infoBoardImg, InfoImgDto.class);
	}
}
