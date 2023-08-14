package com.travel.entity;


import com.travel.constant.ItemSellStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name="item") 
@Getter
@Setter
@ToString
public class Item extends BaseEntity{

	@Id
	@Column(name="item_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id; 
	
	@Column(nullable = false,length = 50) 
	private String itemNm; 
	
	@Column(nullable = false)
	private int price; 
	
	@Column(nullable = false)
	private int stockNumber;
	
	@Lob
	@Column(nullable = false, columnDefinition = "longtext")
	private String itemDetail; 
	
	@Enumerated(EnumType.STRING) 
	private ItemSellStatus itemSellStatus; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
