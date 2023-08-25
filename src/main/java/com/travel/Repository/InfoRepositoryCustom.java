package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travel.Dto.InfoSearchDto;
import com.travel.Dto.MainInfoDto;
import com.travel.entity.InfoBoard;

public interface InfoRepositoryCustom {

	Page<InfoBoard> getAdminInfoPage(InfoSearchDto infoSearchDto, Pageable pageable);
	
	Page<MainInfoDto> getMainInfoPage(InfoSearchDto infoSearchDto, Pageable pageable);
	
	
}
