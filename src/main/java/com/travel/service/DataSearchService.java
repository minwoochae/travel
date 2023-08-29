package com.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.travel.Repository.DataRepository;
import com.travel.entity.DataContent;
import java.util.List;

@Service
public class DataSearchService {

    private final DataRepository dataRepository;

    @Autowired
    public DataSearchService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }
    
    public List<DataContent> searchData(int areaCode, int contentType, String searchTerm) {
        return dataRepository.findByAreaCodeAndContentTypeAndPlaceName(areaCode, contentType, searchTerm);
    }
}
