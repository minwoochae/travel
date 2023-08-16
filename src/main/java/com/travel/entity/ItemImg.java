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

@Getter
@Setter
@Entity
@Table(name = "item_img")
public class ItemImg {

	@Id
	@Column(name = "item_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String imgName;

	private String oriImgName;

	private String imgUrl; // 이미지 조회 경로

	private String repimgYn; // 대표 이미지 여부

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	// 이미지에 대한 정보를 업데이트하는 메소드.
	public void updateItemImg(String oriImgName, String imgName, String imgUrl) {
		this.oriImgName = oriImgName;
		this.imgName = imgName;
		this.imgUrl = imgUrl;
	}
}
