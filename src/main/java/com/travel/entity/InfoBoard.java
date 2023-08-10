package com.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@ToString
public class InfoBoard extends BaseEntity{

	@Id
	@Column(name = "info_board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	// 공지사항 식별자
	
	@Column(name = "info_title")
	private String InfoTitle;	// 공지사항 제목
	
	@Column(name = "info_content")
	private String InfoContent;	// 공지사항 내용
		
	@Column(name="info_img")
	private String InfoImg;	// 공지사항 이미지
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
