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
@Table(name="tourist") 
@Getter
@Setter
@ToString
public class Tourist extends BaseEntity{

	@Id
	@Column(name="tourist_id") 
	@GeneratedValue(strategy = GenerationType.AUTO) 
	private Long id;	// 추천관광지 식별자
	
	@Column(name = "tourist_title")
	private String touristTitle;	// 추천관광지 제목
	
	@Column(name="tourist_content")
	private String touristContent;	// 추천관광지 상세설명
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
