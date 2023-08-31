package com.travel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.Dto.AskFormDto;
import com.travel.Dto.AskImgDto;
import com.travel.Dto.AskResponseFormDto;
import com.travel.Dto.AskResponseSearchDto;
import com.travel.Dto.AskSearchDto;
import com.travel.Dto.MainAskDto;
import com.travel.Dto.MainAskResponseDto;
import com.travel.Repository.AskRepository;
import com.travel.Repository.AskResponseRepository;
import com.travel.entity.AskBoard;
import com.travel.entity.AskBoardImg;
import com.travel.entity.AskResponseBoard;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AskResponseService {

	private final AskResponseRepository askResponseRepository;
	private final AskRepository askRepository;

	// askResponseBoard 테이블에 등록(insert)
	public Long saveAskResponse(Long askBoardId,AskResponseFormDto askResponseFormDto) throws Exception {

		// 글등록
		AskResponseBoard askResponseBoard = askResponseFormDto.createAskResponse();
	
		// AskBoard askBoard = askRepository.findById(askBoardId)
        Optional<AskBoard> askBoardOptional = askRepository.findById(askBoardId);
		
        if (askBoardOptional.isPresent()) {
        	 AskBoard askBoard = askBoardOptional.get();
             askResponseBoard.setAskBoard(askBoard);
        	askResponseRepository.save(askResponseBoard);
        	
        }

		return askResponseBoard.getId();
	}

	// 글 가져오기
	@Transactional(readOnly = true)
	public AskResponseFormDto getAskResponseDtl(Long askResponseBoardId) {

		// ask_response_board 테이블에 있는 데이터를 가져온다.
		try {
			System.out.println("askResponseBoardId: " + askResponseBoardId);
			AskResponseBoard askResponseBoard = askResponseRepository.findById(askResponseBoardId)
					.orElseThrow(EntityNotFoundException::new);			

			// entity -> dto
			AskResponseFormDto askResponseFormDto = AskResponseFormDto.of(askResponseBoard);
			askResponseFormDto.setAskStatus(askResponseBoard.getAskStatus());
			
			return askResponseFormDto;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("여기여기");
			return null;
		}
		

	}

	// 글 수정하기
	public Long updateAskResponse(AskResponseFormDto askResponseFormDto) throws Exception {

		// 엔티티 가져오기
		AskResponseBoard askResponseBoard = askResponseRepository.findById(askResponseFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);

		askResponseBoard.updateAskResponse(askResponseFormDto);

		return askResponseBoard.getId();
	}

	@Transactional(readOnly = true)
	public Page<AskResponseBoard> getAdminAskResponsePage(AskResponseSearchDto askResponseSearchDto,
			Pageable pageable) {

		Page<AskResponseBoard> askResponsePage = askResponseRepository.getAdminAskResponsePage(askResponseSearchDto,
				pageable);

		return askResponsePage;
	}

	@Transactional(readOnly = true)
	public Page<MainAskResponseDto> getMainAskResponsePage(AskResponseSearchDto askResponseSearchDto,
			Pageable pageable) {
		Page<MainAskResponseDto> mainAskResponsePage = askResponseRepository
				.getMainAskResponsePage(askResponseSearchDto, pageable);
		return mainAskResponsePage;
	}

	// 글 삭제
	public void deleteAskResponse(Long askResponseBoardId) {
		AskResponseBoard askResponseBoard = askResponseRepository.findById(askResponseBoardId).orElseThrow(EntityNotFoundException::new);

		askResponseRepository.delete(askResponseBoard);
	}
}
