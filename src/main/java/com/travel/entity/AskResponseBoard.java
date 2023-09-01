package com.travel.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.travel.Dto.AskResponseFormDto;
import com.travel.constant.AskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name="ask_response_board") 
@Getter
@Setter
@ToString
public class AskResponseBoard extends BaseEntity{
	
	@Id
	@Column(name="ask_response_board_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name="ask_response_title") 
	private String askResponseTitle;
	
	@Column(name="ask_response_content") 
	private String askResponseContent;
	
	@Enumerated(EnumType.STRING)
	private AskStatus askStatus;
	
	@OneToOne
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name="ask_board_id")
	private AskBoard askBoard;
	
	
	// 엔티티 수정
	public void updateAskResponse(AskResponseFormDto askResponseFormDto) {
		this.askResponseTitle = askResponseFormDto.getAskResponseTitle();
		this.askResponseContent = askResponseFormDto.getAskResponseContent();
	}
}
