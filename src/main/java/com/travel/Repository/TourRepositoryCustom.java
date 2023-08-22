package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travel.Dto.MainTourDto;
import com.travel.Dto.TourSearchDto;
import com.travel.entity.Tourist;


public interface TourRepositoryCustom {

	Page<Tourist> getAdminTourPage(TourSearchDto tourSearchDto, Pageable pageable);

	Page<MainTourDto> getMainTourPage(TourSearchDto tourSearchDto, Pageable pageable);
}
