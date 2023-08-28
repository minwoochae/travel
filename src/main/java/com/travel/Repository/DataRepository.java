package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.travel.entity.DataContent;

public interface DataRepository extends JpaRepository<DataContent, Long> {
	List<DataContent> findByAreaCodeInAndContentTypeIn(List<Integer> areaCodes, List<Integer> contentTypes);
	
}
