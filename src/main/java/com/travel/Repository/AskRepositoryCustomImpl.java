package com.travel.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.Dto.AskSearchDto;
import com.travel.Dto.MainAskDto;
import com.travel.Dto.QMainAskDto;
import com.travel.entity.AskBoard;
import com.travel.entity.QAskBoard;
import com.travel.entity.QAskBoardImg;

import jakarta.persistence.EntityManager;

public class AskRepositoryCustomImpl implements AskRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public AskRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	private BooleanExpression searchByLike(String searchBy, String searchQuery) {

		if (StringUtils.equals("askTitle", searchBy)) {
			// 등록자로 검색 시
			return QAskBoard.askBoard.askTitle.like("%" + searchQuery + "%");
		} else if (StringUtils.equals("createBy", searchBy)) {
			return QAskBoard.askBoard.createBy.like("%" + searchQuery + "%"); // create_by like %검색어%
		}

		return null;
	}

	@Override
	public Page<AskBoard> getAdminAskPage(AskSearchDto askSearchDto, Pageable pageable) {

		List<AskBoard> content = queryFactory.selectFrom(QAskBoard.askBoard)
				.where(searchByLike(askSearchDto.getSearchBy(), askSearchDto.getSearchQuery()))
				.orderBy(QAskBoard.askBoard.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetch();

		long total = queryFactory.select(Wildcard.count).from(QAskBoard.askBoard)
				.where(searchByLike(askSearchDto.getSearchBy(), askSearchDto.getSearchQuery())).fetchOne();

		return new PageImpl<>(content, pageable, total);
	}

	private BooleanExpression askTitleLike(String searchQuery) {
		return StringUtils.isEmpty(searchQuery) ? null : QAskBoard.askBoard.askTitle.like("%" + searchQuery + "%");
	}

	@Override
	public Page<MainAskDto> getMainAskPage(AskSearchDto askSearchDto, Pageable pageable) {

		QAskBoard askBoard = QAskBoard.askBoard;
		QAskBoardImg askBoardImg = QAskBoardImg.askBoardImg;

		List<MainAskDto> content = queryFactory
				.select(new QMainAskDto(askBoard.id, askBoard.askTitle, askBoard.askContent)).from(askBoardImg)
				.join(askBoardImg.askBoard, askBoard).where(askTitleLike(askSearchDto.getSearchQuery()))
				.orderBy(askBoard.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();

		long total = queryFactory.select(Wildcard.count).from(askBoardImg).join(askBoardImg.askBoard, askBoard)
				.fetchOne();

		return new PageImpl<>(content, pageable, total);

	}

}
