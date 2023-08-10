package com.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	@Column(name="ask_response_id") 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;
	
	@Column(name="ask_response_title") 
	private String askResponseTitle;
	
	@Column(name="ask_response_content") 
	private String askResponseContent;
	
	@Column(name="ask_response_img") 
	private String askResponseImg;
	
	@OneToOne
	@JoinColumn(name="ask_board")
	private AskBoard askBoard;
}
