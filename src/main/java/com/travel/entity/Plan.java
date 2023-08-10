package com.travel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name="plan") 
@Getter
@Setter
@ToString
public class Plan {
	
	@Id
	@Column(name="plan_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "plan_title")
	private String planTitle;
	
	@Column(name = "plan_date")
	private String planDate;
	
	@ManyToOne(fetch =FetchType.LAZY)
	private Member member;
	
}