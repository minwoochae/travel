package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.travel.entity.InfoBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoFormDto {

	private Long id;
	
	private String infoTitle;
	
	private String infoContent;
	
	private List<InfoImgDto> infoImgDtoList = new ArrayList<>();
	
	private List<Long> infoImgIds = new ArrayList<>();
	
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto-> entity
	public InfoBoard createInfo() {
		return modelMapper.map(this, InfoBoard.class);
	}
	
	// entity -> dto
	public static InfoFormDto of(InfoBoard infoBoard) {
		return modelMapper.map(infoBoard, InfoFormDto.class);
	}
}
