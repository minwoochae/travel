package com.travel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.Dto.InfoFormDto;
import com.travel.Repository.InfoRepository;
import com.travel.entity.InfoBoard;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InfoService {

	// private final InfoRepository infoRepository;
	
	/*
	// infoBoard 테이블에 공지등록(insert)
	public Long saveInfo(InfoFormDto infoFormDto, MultipartFile infoImg) throws Exception {
		
		// 공지등록
		InfoBoard infoBoard = infoFormDto.createInfo();
		infoRepository.save(infoBoard);
		
		// 이미지 등록
		
		return infoBoard.getId();
	}
	*/
	
	// 글 가져오기
	
	
	// 글 수정하기
	
	// 글 삭제
	
}
