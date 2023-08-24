package com.travel.entity;

import com.travel.Dto.InfoFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="info_board")
@Getter
@Setter
@ToString
public class InfoBoard extends BaseEntity{

	@Id
	@Column(name = "info_board_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	// 공지사항 식별자
	
	@Column(name = "info_title")
	private String infoTitle;	// 공지사항 제목
	
	@Lob
	@Column(name = "info_content", columnDefinition = "longtext")
	private String infoContent;	// 공지사항 내용
		

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	// 엔티티 수정
	public void updateInfo(InfoFormDto infoFormDto) {
		this.infoTitle = infoFormDto.getInfoTitle();
		this.infoContent = infoFormDto.getInfoContent();
	}
}
