package com.travel.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity 
@Table(name="pay")
@Getter
@Setter
@ToString
public class Pay{
	
	@Id
	@Column(name="pay_id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name="price") 
	private int price;
	
	@Column(name="pay_no") 
	private String payNo;
	
	@Column(name = "pay_date")
	private String payDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "item_id")
	private Item item;
	
	
	
	
}