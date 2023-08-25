package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.Dto.InfoSearchDto;
import com.travel.Dto.MainInfoDto;

import com.travel.Dto.QMainInfoDto;
import com.travel.entity.InfoBoard;
import com.travel.entity.QInfoBoard;
import com.travel.entity.QInfoBoardImg;


import jakarta.persistence.EntityManager;

public class InfoRepositoryCustomImpl implements InfoRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public InfoRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("infoTitle", searchBy)) {
			// 등록자로 검색 시
			return QInfoBoard.infoBoard.infoTitle.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createBy", searchBy)) {
			return QInfoBoard.infoBoard.createBy.like("%" + searchQuery + "%"); // create_by like %검색어%
		}

		return null;
	}

	@Override
	public Page<InfoBoard> getAdminInfoPage(InfoSearchDto infoSearchDto, Pageable pageable) {

		List<InfoBoard> content = queryFactory.selectFrom(QInfoBoard.infoBoard)
				.where(searchByLike(infoSearchDto.getSearchBy(), infoSearchDto.getSearchQuery()))
				.orderBy(QInfoBoard.infoBoard.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetch();

		long total = queryFactory.select(Wildcard.count).from(QInfoBoard.infoBoard)
				.where(searchByLike(infoSearchDto.getSearchBy(), infoSearchDto.getSearchQuery())).fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression infoTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QInfoBoard.infoBoard.infoTitle.like("%" + searchQuery + "%");
	}

	@Override
	public Page<MainInfoDto> getMainInfoPage(InfoSearchDto infoSearchDto, Pageable pageable) {

		QInfoBoard infoBoard = QInfoBoard.infoBoard;
		QInfoBoardImg infoBoardImg = QInfoBoardImg.infoBoardImg;

		List<MainInfoDto> content = queryFactory
				.select(new QMainInfoDto(infoBoard.id, infoBoard.infoTitle, infoBoard.infoContent)).from(infoBoardImg)
				.join(infoBoardImg.infoBoard, infoBoard).where(infoBoardImg.repimgYn.eq("Y"))
				.where(infoTitleLike(infoSearchDto.getSearchQuery())).orderBy(infoBoard.id.desc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

		long total = queryFactory.select(Wildcard.count).from(infoBoardImg).join(infoBoardImg.infoBoard, infoBoard)
				.where(infoBoardImg.repimgYn.eq("Y")).where(infoTitleLike(infoSearchDto.getSearchQuery())).fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

}
