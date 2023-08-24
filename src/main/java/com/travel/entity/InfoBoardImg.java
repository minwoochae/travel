package com.travel.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "info_board_img")
public class InfoBoardImg extends BaseEntity{

	@Id
	@Column(name = "info_img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String imgName; // 이미지 이름
	
	private String oriImgName; //원본 이미지 
	
	private String imgUrl; //이미지 조회 경로
	
	private String repimgYn; // 대표이미지 여부
	
	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "info_board_id")
	private InfoBoard infoBoard;
	
	// 이미지 업데이트
		public void updateInfoImg(String oriImgName, String imgName, String imgUrl) {
			this.oriImgName = oriImgName;
			this.imgName = imgName;
			this.imgUrl = imgUrl;
		}
}
