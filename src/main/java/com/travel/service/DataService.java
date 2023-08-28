package com.travel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.Repository.DataRepository;
import com.travel.entity.DataContent;

import java.util.List;

@Service
public class DataService {

	@Autowired
    private DataRepository dataRepository;

    public List<DataContent> getDataByAreaCodeAndContentType(
            List<Integer> areaCodes,
            List<Integer> contentTypes
    ) {
        return dataRepository.findByAreaCodeInAndContentTypeIn(areaCodes, contentTypes);
    }

   
}