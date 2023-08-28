package com.travel.Dto;

import org.modelmapper.ModelMapper;

import com.travel.constant.AskStatus;
import com.travel.entity.AskResponseBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskResponseFormDto {

	Long id;
	
	private String askResponseTitle;
	
	private String askResponseContent;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	private AskStatus askStatus;
	
	private Long askBoardId; 
	
	// dto -> entity
	public AskResponseBoard createAskResponse() {
		return modelMapper.map(this, AskResponseBoard.class);
	}
	
	// entity -> dto
	public static  AskResponseFormDto of(AskResponseBoard askResponseBoard) {
		return modelMapper.map(askResponseBoard, AskResponseFormDto.class);
	}
	
	public Long getAskBoardId() {
	       return askBoardId;
	}

	public void setAskBoardId(Long askBoardId) {
	       this.askBoardId = askBoardId;
	}
}
