package com.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.Repository.DataRepository;
import com.travel.entity.DataContent;

import java.util.List;

@Service
public class DataService {

    private final DataRepository dataRepository;

    @Autowired
    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public List<DataContent> getAllAreaCodes() {
        return dataRepository.findAllAreaCodes();
    }

    public List<DataContent> findByContentTypeAndAreaCode(int contentType, int areaCode) {
        return dataRepository.findByContentTypeAndAreaCode(contentType, areaCode);
    }
    
    
}