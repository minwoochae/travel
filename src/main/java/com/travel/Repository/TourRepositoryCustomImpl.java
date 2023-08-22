package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.Dto.MainItemDto;
import com.travel.Dto.MainTourDto;
import com.travel.Dto.QMainItemDto;
import com.travel.Dto.QMainTourDto;
import com.travel.Dto.TourSearchDto;
import com.travel.entity.QItem;
import com.travel.entity.QTourist;
import com.travel.entity.QTouristImg;
import com.travel.entity.Tourist;

import jakarta.persistence.EntityManager;

public class TourRepositoryCustomImpl implements TourRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	public TourRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		
		if(StringUtils.equals("touristTitle", searchBy)) {
			// 등록자로 검색 시 
			return QTourist.tourist.touristTitle.like("%"+ searchQuery + "%");
		}
		else if (StringUtils.equals("createBy", searchBy)) {
			return QTourist.tourist.createBy.like("%"+ searchQuery + "%"); // create_by like %검색어%
		}
		
		return null;
	}

	@Override
	public Page<Tourist> getAdminTourPage(TourSearchDto tourSearchDto, Pageable pageable) {
		
		List<Tourist> content = queryFactory.selectFrom(QTourist.tourist)
				.where(searchByLike(tourSearchDto.getSearchBy(), tourSearchDto.getSearchQuery()))
				.orderBy(QTourist.tourist.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory.select(Wildcard.count).from(QTourist.tourist)
				.where(searchByLike(tourSearchDto.getSearchBy(), tourSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression touristTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery)? 
				null : QTourist.tourist.touristTitle.like("%" + searchQuery + "%"); 
	}
	
	@Override
	public Page<MainTourDto> getMainTourPage(TourSearchDto tourSearchDto, Pageable pageable) {
		
		QTourist tourist = QTourist.tourist;
		QTouristImg touristImg = QTouristImg.touristImg;
		
		List<MainTourDto> content = queryFactory.select(
				new QMainTourDto(
						tourist.id, tourist.touristTitle, tourist.touristContent)
				)
				.from(touristImg)
				.join(touristImg.tourist, tourist)
				.where(touristImg.repimgYn.eq("Y"))
				.where(touristTitleLike(tourSearchDto.getSearchQuery()))
				.orderBy(tourist.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		long total = queryFactory
				.select(Wildcard.count)
				.from(touristImg)
				.join(touristImg.tourist, tourist)
				.where(touristImg.repimgYn.eq("Y"))
				.where(touristTitleLike(tourSearchDto.getSearchQuery()))
				.fetchOne();
		
		return new PageImpl<>(content,pageable,total);
		
	}
	
	
}
