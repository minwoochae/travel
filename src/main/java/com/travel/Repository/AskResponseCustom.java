package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travel.Dto.AskResponseSearchDto;
import com.travel.Dto.MainAskResponseDto;
import com.travel.entity.AskResponseBoard;

public interface AskResponseCustom {

	Page<AskResponseBoard> getAdminAskResponsePage(AskResponseSearchDto askResponseSearchDto, Pageable pageable);
	
	Page<MainAskResponseDto> getMainAskResponsePage(AskResponseSearchDto askResponseSearchDto, Pageable pageable);
}
