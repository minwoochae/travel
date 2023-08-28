package com.travel.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.Dto.PlanContentDto;
import com.travel.Repository.DataRepository;
import com.travel.entity.DataContent;

@Service
public class PlanContentService {
    private final DataRepository dataRepository;
    private final ApiService apiService;

    @Autowired
    public PlanContentService(DataRepository dataRepository, ApiService apiService) {
        this.dataRepository = dataRepository;
        this.apiService = apiService;
    }

    public void DataSave() {
    	 List<String> apiUrls = new ArrayList<>();
    	 apiUrls.add("https://apis.data.go.kr/B551011/KorService1/areaBasedSyncList1?numOfRows=2000&pageNo=1&MobileOS=ETC&MobileApp=TEST&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D&showflag=1&listYN=Y&arrange=Y&contentTypeId=12");
    	 apiUrls.add("https://apis.data.go.kr/B551011/KorService1/areaBasedSyncList1?numOfRows=2000&pageNo=1&MobileOS=ETC&MobileApp=TEST&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D&showflag=1&listYN=Y&arrange=Y&contentTypeId=32");
    	 apiUrls.add("https://apis.data.go.kr/B551011/KorService1/areaBasedSyncList1?numOfRows=2000&pageNo=1&MobileOS=ETC&MobileApp=TEST&serviceKey=bWi7itZDsVW8U1exI%2BALv2Eys5Aq6ELHC0tumPmSeA%2Bb221ygrItwTu0OKj%2BXDcb61FoPzn5Ut7PlCRAHy94Zw%3D%3D&showflag=1&listYN=Y&arrange=Y&contentTypeId=39");

    	    for (String apiUrl : apiUrls) {
    	        List<PlanContentDto> contentList = apiService.fetchApiData(apiUrl);

    	        for (PlanContentDto contentDto : contentList) {
    	            DataContent dataContent = new DataContent();
    	            dataContent.setPlaceName(contentDto.getPlaceName());
    	            dataContent.setPlaceLatitude(contentDto.getPlaceLatitude());
    	            dataContent.setPlaceLongitude(contentDto.getPlaceLongitude());
    	            dataContent.setPlaceimg(contentDto.getPlace_img());
    	            dataContent.setPlaceAddress(contentDto.getPlaceAddress());
    	            dataContent.setAreaCode(contentDto.getArea_code());
    	            dataContent.setSigunguCode(contentDto.getSigungu_code());
    	            dataContent.setContentId(contentDto.getContent_id());
    	            dataContent.setContentType(contentDto.getContent_type());

    	            // PlanContent 엔터티를 데이터베이스에 저장
    	            dataRepository.save(dataContent);
    	        }
    	    }
    }
}
