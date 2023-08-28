package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.Dto.AskResponseSearchDto;
import com.travel.Dto.MainAskResponseDto;
import com.travel.Dto.QMainAskResponseDto;
import com.travel.entity.AskResponseBoard;

import com.travel.entity.QAskResponseBoard;

import jakarta.persistence.EntityManager;

public class AskResponseCustomImpl implements AskResponseCustom{

	private JPAQueryFactory queryFactory;

	public AskResponseCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("askResponseTitle", searchBy)) {
			// 등록자로 검색 시
			return QAskResponseBoard.askResponseBoard.askResponseTitle.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createBy", searchBy)) {
			return QAskResponseBoard.askResponseBoard.createBy.like("%" + searchQuery + "%"); 
		}

		return null;
	}
	
	
	@Override
	public Page<AskResponseBoard> getAdminAskResponsePage(AskResponseSearchDto askResponseSearchDto, Pageable pageable) {

		List<AskResponseBoard> content = queryFactory.selectFrom(QAskResponseBoard.askResponseBoard)
			
				.orderBy(QAskResponseBoard.askResponseBoard.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetch();

		long total = queryFactory.select(Wildcard.count).from(QAskResponseBoard.askResponseBoard)
			.fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression askResponseTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QAskResponseBoard.askResponseBoard.askResponseTitle.like("%" + searchQuery + "%");
	}
	
	@Override
	public Page<MainAskResponseDto> getMainAskResponsePage(AskResponseSearchDto askResponseSearchDto, Pageable pageable) {

	    QAskResponseBoard askResponseBoard = QAskResponseBoard.askResponseBoard;

	    List<MainAskResponseDto> content = queryFactory
	        .select(new QMainAskResponseDto(askResponseBoard.id, askResponseBoard.askResponseTitle, askResponseBoard.askResponseContent))
	        .from(askResponseBoard)
	        .orderBy(askResponseBoard.id.desc())
	        .offset(pageable.getOffset())
	        .limit(pageable.getPageSize())
	        .fetch();

	    long total = queryFactory
	        .select(Wildcard.count)
	        .from(askResponseBoard)
	        .fetchOne();

	    return new PageImpl<>(content, pageable, total);
	}
}

