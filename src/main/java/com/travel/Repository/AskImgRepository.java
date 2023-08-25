package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.AskBoardImg;



public interface AskImgRepository extends JpaRepository<AskBoardImg, Long>{

	List<AskBoardImg> findByAskBoardIdOrderByIdAsc(Long askBoardId);
	
	AskBoardImg findByAskBoardIdAndRepimgYn(Long askBoardId, String repimgYn);
}
