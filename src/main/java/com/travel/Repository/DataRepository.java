package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.travel.entity.DataContent;

public interface DataRepository extends JpaRepository<DataContent, Long>{
	List<DataContent> findByContentType(int content_type);
	
	// 새로운 메소드 추가: 지역 코드 목록을 가져오는 메소드
    @Query(value = "select areaCode from DataContent")
    List<DataContent> findAllAreaCodes();
    
    List<DataContent> findByContentTypeAndAreaCode(int contentType, int areaCode);
    
    
}
