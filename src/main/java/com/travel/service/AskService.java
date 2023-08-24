package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.Dto.AskFormDto;
import com.travel.Dto.AskImgDto;
import com.travel.Dto.AskSearchDto;
import com.travel.Dto.InfoFormDto;
import com.travel.Dto.InfoImgDto;
import com.travel.Dto.InfoSearchDto;
import com.travel.Dto.MainAskDto;
import com.travel.Dto.MainInfoDto;
import com.travel.Repository.AskImgRepository;
import com.travel.Repository.AskRepository;
import com.travel.entity.AskBoard;
import com.travel.entity.AskBoardImg;
import com.travel.entity.InfoBoard;
import com.travel.entity.InfoBoardImg;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AskService {

	private final AskRepository askRepository;
	private final AskImgRepository askImgRepository;
	private final AskImgService askImgService;
	
	// askBoard 테이블에 등록(insert)
	public Long saveAsk(AskFormDto askFormDto, List<MultipartFile> askImgFileList) throws Exception {
		
		// 글등록
		AskBoard askBoard = askFormDto.createAsk();
		askRepository.save(askBoard);
		
		// 이미지 등록
		for(int i=0; i<askImgFileList.size(); i++) {
			AskBoardImg askBoardImg = new AskBoardImg();
			askBoardImg.setAskBoard(askBoard);
			
			// 첫 이미지 대표이미지 지정
			if( i == 0) {
				askBoardImg.setRepimgYn("Y");
			} else {
				askBoardImg.setRepimgYn("N");				
			}
			askImgService.saveAskImg(askBoardImg, askImgFileList.get(i));
		}
		
		return askBoard.getId();
	}
	
	
	// 글 가져오기
		@Transactional(readOnly = true)
		public AskFormDto getAskDtl(Long askBoardId) {
			
			// ask_board_img 테이블의 이미지를 가져온다
			List<AskBoardImg> askBoardImgList = askImgRepository.findByAskBoardIdOrderByIdAsc(askBoardId);
			
			// ask_board_img 엔티티 -> AskImgDto로 변환
			List<AskImgDto> askImgDtoList = new ArrayList<>();
			
			for(AskBoardImg askImg : askBoardImgList) {
				AskImgDto askImgDto = AskImgDto.of(askImg);
				askImgDtoList.add(askImgDto);
			}
			
			// ask_board 테이블에 있는 데이터를 가져온다.
			AskBoard askBoard = askRepository.findById(askBoardId).orElseThrow(EntityNotFoundException::new);
			
			// entity -> dto
			AskFormDto askFormDto = AskFormDto.of(askBoard);
			
			// tourFormDto에 이미지 넣기
			askFormDto.setAskImgDtoList(askImgDtoList);
			
			return askFormDto;
		}
		
		
		// 글 수정하기
		public Long updateAsk(AskFormDto askFormDto, List<MultipartFile> askImgFileList) throws Exception {
			
			// 엔티티 가져오기
			AskBoard askBoard = askRepository.findById(askFormDto.getId()).orElseThrow(EntityNotFoundException::new);
			
			askBoard.updateAsk(askFormDto);
			
			// 이미지 테이블 변경
			List<Long> askImgIds = askFormDto.getAskImgIds();
			
			for(int i = 0; i < askImgFileList.size(); i++) {
				askImgService.updateAskImg(askImgIds.get(i), askImgFileList.get(i));
			}
			
			return askBoard.getId();
		}
		
		
		@Transactional(readOnly = true)
		public Page<AskBoard> getAdminAskPage(AskSearchDto askSearchDto, Pageable pageable) {
			
			Page<AskBoard> askPage = askRepository.getAdminAskPage(askSearchDto, pageable);
			
			return askPage;
		}
		
		@Transactional(readOnly = true)
		public Page<MainAskDto> getMainAskPage(AskSearchDto askSearchDto, Pageable pageable) {
			Page<MainAskDto> mainAskPage = askRepository.getMainAskPage(askSearchDto, pageable);
			return mainAskPage;
		}	
		
		// 글 삭제
		public void deleteAsk(Long askBoardId) {
			AskBoard askBoard = askRepository.findById(askBoardId)
					.orElseThrow(EntityNotFoundException::new);
			
			askRepository.delete(askBoard);
		}
}
