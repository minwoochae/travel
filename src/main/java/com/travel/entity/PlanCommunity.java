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
@Table(name="item") 
@Getter
@Setter
@ToString
public class PlanCommunity extends BaseEntity{

	@Id
	@Column(name="plan_community_id") 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="plan_community_id") 
	private String communityTitle;
	
	@Column(name="plan_community_id") 
	private String communityContent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id")
	private Plan plan;
}
