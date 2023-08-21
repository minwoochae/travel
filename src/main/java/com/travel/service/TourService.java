package com.travel.service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.travel.Dto.TourFormDto;
import com.travel.Dto.TourImgDto;
import com.travel.Dto.TourSearchDto;
import com.travel.Repository.TourImgRepository;
import com.travel.Repository.TourRepository;
import com.travel.entity.Tourist;
import com.travel.entity.TouristImg;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TourService {

	private final TourRepository tourRepository;
	private final TourImgRepository tourImgRepository;
	private final TourImgService tourImgService;
	
	// 테이블에 추천관광지 등록
	public Long saveTour(TourFormDto tourFormDto, List<MultipartFile> tourImgFileList) throws Exception {
		Tourist tourist = tourFormDto.createTour();
		tourRepository.save(tourist);
		
		// 이미지 등록
		for(int i=0; i<tourImgFileList.size(); i++) {
			TouristImg touristImg = new TouristImg();
			touristImg.setTourist(tourist);
			
			// 첫 이미지 대표이미지 지정
			if( i == 0) {
				touristImg.setRepimgYn("Y");
			} else {
				touristImg.setRepimgYn("N");				
			}
			tourImgService.saveTourImg(touristImg, tourImgFileList.get(i));
		}
		
		return tourist.getId();
	}
	
	// 추천관광지 가져오기
	@Transactional(readOnly = true)
	public TourFormDto getTourDtl(Long touristId) {
		
		// tourist_img 테이블의 이미지를 가져온다
		List<TouristImg> touristImgList = tourImgRepository.findByTouristIdOrderByIdAsc(touristId);
		
		// tourist_img 엔티티 -> TourImgDto로 변환
		List<TourImgDto> tourImgDtoList = new ArrayList<>();
		for(TouristImg touristImg : touristImgList) {
			TourImgDto tourImgDto = TourImgDto.of(touristImg);
			tourImgDtoList.add(tourImgDto);
		}
		
		// tourist 테이블에 있는 데이터를 가져온다.
		Tourist tourist = tourRepository.findById(touristId).orElseThrow(EntityNotFoundException::new);
		
		// entity -> dto
		TourFormDto tourFormDto = TourFormDto.of(tourist);
		
		// tourFormDto에 이미지 넣기
		tourFormDto.setTourImgDtoList(tourImgDtoList);
		
		return tourFormDto;
	}
	
	// 수정하기
	public Long updateTour(TourFormDto tourFormDto, List<MultipartFile> tourImgFileList) throws Exception {
		
		// 엔티티 가져오기
		Tourist tourist = tourRepository.findById(tourFormDto.getId()).orElseThrow(EntityNotFoundException::new);
		
		tourist.updateTour(tourFormDto);
		
		// 이미지 테이블 변경
		List<Long> tourImgIds = tourFormDto.getTourImgIds();
		
		for(int i = 0; i < tourImgFileList.size(); i++) {
			tourImgService.updateTourImg(tourImgIds.get(i), tourImgFileList.get(i));
		}
		
		return tourist.getId();
	}
	
	@Transactional(readOnly = true)
	public Page<Tourist> getAdminPage(TourSearchDto tourSearchDto, Pageable pageable) {
		
		// Page<Tourist> tourPage = tourRepository.getAdminTourPage(tourSearchDto, pageable);
		
		return null;
	}
	
	
	// 삭제하기
	public void deleteTour(Long touristId) {
		Tourist tourist = tourRepository.findById(touristId)
				.orElseThrow(EntityNotFoundException::new);
		
		tourRepository.delete(tourist);
	}
}
