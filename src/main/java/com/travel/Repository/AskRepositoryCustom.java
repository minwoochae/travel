package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travel.Dto.AskSearchDto;
import com.travel.Dto.MainAskDto;
import com.travel.entity.AskBoard;


public interface AskRepositoryCustom {

	Page<AskBoard> getAdminAskPage(AskSearchDto askSearchDto, Pageable pageable);
	
	Page<MainAskDto> getMainAskPage(AskSearchDto askSearchDto, Pageable pageable);
	
}
