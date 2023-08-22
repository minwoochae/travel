package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.TouristImg;

public interface TourImgRepository extends JpaRepository<TouristImg, Long>{

	List<TouristImg> findByTouristIdOrderByIdAsc(Long touristId);
	
	TouristImg findByTouristIdAndRepimgYn(Long touristId, String repingYn);

	
}
