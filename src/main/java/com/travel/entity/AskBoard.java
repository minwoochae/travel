package com.travel.entity;

import com.travel.constant.Ask;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

@Entity //엔티티 클래스로 정의. 꼭 있어야 함. 
@Table(name="ask_board") //테이블 이름 지정
@Getter
@Setter
@ToString
public class AskBoard extends BaseEntity{
	
	@Id
	@Column(name="ask_board_id") //테이블로 생성될 때 컬럼이름을 지정해준다. 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name = "ask_title")
	private String askTitle;
	
	@Column(name = "ask_content")
	private String askContent;
	
	@Column(name = "ask_img")
	private String askImg;
	
	@Enumerated(EnumType.STRING)
	private Ask ask;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
}
