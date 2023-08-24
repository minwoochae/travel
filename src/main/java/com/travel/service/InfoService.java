package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.Dto.InfoFormDto;
import com.travel.Dto.InfoImgDto;
import com.travel.Dto.InfoSearchDto;
import com.travel.Dto.MainInfoDto;
import com.travel.Dto.MainTourDto;
import com.travel.Dto.TourFormDto;
import com.travel.Dto.TourImgDto;
import com.travel.Dto.TourSearchDto;
import com.travel.Repository.InfoImgRepository;
import com.travel.Repository.InfoRepository;
import com.travel.entity.InfoBoard;
import com.travel.entity.InfoBoardImg;
import com.travel.entity.Tourist;
import com.travel.entity.TouristImg;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InfoService {

	private final InfoRepository infoRepository;
	private final InfoImgRepository infoImgRepository;
	private final InfoImgService infoImgService;
	
	
	// infoBoard 테이블에 공지등록(insert)
	public Long saveInfo(InfoFormDto infoFormDto, List<MultipartFile> infoImgFileList) throws Exception {
		
		// 공지등록
		InfoBoard infoBoard = infoFormDto.createInfo();
		infoRepository.save(infoBoard);
		
		// 이미지 등록
		for(int i=0; i<infoImgFileList.size(); i++) {
			InfoBoardImg infoBoardImg = new InfoBoardImg();
			infoBoardImg.setInfoBoard(infoBoard);
			
			// 첫 이미지 대표이미지 지정
			if( i == 0) {
				infoBoardImg.setRepimgYn("Y");
			} else {
				infoBoardImg.setRepimgYn("N");				
			}
			infoImgService.saveInfoImg(infoBoardImg, infoImgFileList.get(i));
		}
		
		return infoBoard.getId();
	}
	
	
	// 글 가져오기
	@Transactional(readOnly = true)
	public InfoFormDto getInfoDtl(Long infoBoardId) {
		
		// tourist_img 테이블의 이미지를 가져온다
		List<InfoBoardImg> infoBoardImgList = infoImgRepository.findByInfoBoardIdOrderByIdAsc(infoBoardId);
		
		// tourist_img 엔티티 -> TourImgDto로 변환
		List<InfoImgDto> infoImgDtoList = new ArrayList<>();
		for(InfoBoardImg infoImg : infoBoardImgList) {
			InfoImgDto infoImgDto = InfoImgDto.of(infoImg);
			infoImgDtoList.add(infoImgDto);
		}
		
		// tourist 테이블에 있는 데이터를 가져온다.
		InfoBoard infoBoard = infoRepository.findById(infoBoardId).orElseThrow(EntityNotFoundException::new);
		
		// entity -> dto
		InfoFormDto infoFormDto = InfoFormDto.of(infoBoard);
		
		// tourFormDto에 이미지 넣기
		infoFormDto.setInfoImgDtoList(infoImgDtoList);
		
		return infoFormDto;
	}
	

	// 글 수정하기
	public Long updateInfo(InfoFormDto infoFormDto, List<MultipartFile> infoImgFileList) throws Exception {
		
		// 엔티티 가져오기
		InfoBoard infoBoard = infoRepository.findById(infoFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		infoBoard.updateInfo(infoFormDto);
		
		// 이미지 테이블 변경
		List<Long> infoImgIds = infoFormDto.getInfoImgIds();
		
		for(int i = 0; i < infoImgFileList.size(); i++) {
			infoImgService.updateInfoImg(infoImgIds.get(i), infoImgFileList.get(i));
		}
		
		return infoBoard.getId();
	}
	
	
	@Transactional(readOnly = true)
	public Page<InfoBoard> getAdminInfoPage(InfoSearchDto infoSearchDto, Pageable pageable) {
		
		Page<InfoBoard> infoPage = infoRepository.getAdminInfoPage(infoSearchDto, pageable);
		
		return infoPage;
	}
	
	@Transactional(readOnly = true)
	public Page<MainInfoDto> getMainInfoPage(InfoSearchDto infoSearchDto, Pageable pageable) {
		Page<MainInfoDto> mainInfoPage = infoRepository.getMainInfoPage(infoSearchDto, pageable);
		return mainInfoPage;
	}
	
	
	// 글 삭제
	public void deleteInfo(Long infoBoardId) {
		InfoBoard infoBoard = infoRepository.findById(infoBoardId)
				.orElseThrow(EntityNotFoundException::new);
		
		infoRepository.delete(infoBoard);
	}
	
}
