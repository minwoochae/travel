package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.entity.DataContent;

public interface DataRepository extends JpaRepository<DataContent, Long> {
	List<DataContent> findByAreaCodeInAndContentTypeIn(List<Integer> areaCodes, List<Integer> contentTypes);
	
	 // 검색어에 따른 쿼리 작성
    @Query("SELECT d FROM DataContent d WHERE d.areaCode = :areaCode AND d.contentType = :contentType " +
            "AND (:searchTerm IS NULL OR LOWER(d.placeName) LIKE %:searchTerm%)")
    List<DataContent> findByAreaCodeAndContentTypeAndPlaceName(
            @Param("areaCode") int areaCode,
            @Param("contentType") int contentType,
            @Param("searchTerm") String searchTerm
    );
}
