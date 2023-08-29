package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.travel.constant.AskStatus;
import com.travel.entity.AskBoard;
import com.travel.entity.InfoBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskFormDto {

	private Long id;
	
	private String askTitle;
	
	private String askContent;
	
	private String createBy;

	private List<AskImgDto> askImgDtoList = new ArrayList<>();
	
	private List<Long> askImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto-> entity
		public AskBoard createAsk() {
			return modelMapper.map(this, AskBoard.class);
		}
		
		// entity -> dto
		public static AskFormDto of(AskBoard askBoard) {
			return modelMapper.map(askBoard, AskFormDto.class);
		}
	
}
