package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.entity.AskBoardImg;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskImgDto {

	private Long id;

	private String imgName;

	private String oriImgName;

	private String imgUrl; 

	private String repimgYn;

	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity > dto로 전환
	public static AskImgDto of(AskBoardImg askBoardImg) {
		return modelMapper.map(askBoardImg,AskImgDto.class);
	}
}
