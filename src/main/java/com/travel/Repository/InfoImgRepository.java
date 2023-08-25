package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.entity.InfoBoardImg;

public interface InfoImgRepository extends JpaRepository<InfoBoardImg, Long>{

	List<InfoBoardImg> findByInfoBoardIdOrderByIdAsc(Long infoBoardId);
	
	InfoBoardImg findByInfoBoardIdAndRepimgYn(Long infoBoardId, String repimgYn);
}
