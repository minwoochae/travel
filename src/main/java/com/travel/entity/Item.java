package com.travel.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.travel.Dto.ItemFormDto;
import com.travel.constant.ItemSellStatus;
import com.travel.exception.OutOfStockException;

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
	@OnDelete(action= OnDeleteAction.CASCADE)
	@JoinColumn(name = "member_id")
	private Member member;
	
	//item 엔티티 수정
		public void updateItem(ItemFormDto itemFormDto) {
			this.itemNm = itemFormDto.getItemNm();	
			this.price = itemFormDto.getPrice();
			this.stockNumber = itemFormDto.getStockNumber();
			this.itemDetail = itemFormDto.getItemDetail();
			this.itemSellStatus = itemFormDto.getItemSellStatus();
			
		}
		
		//재고를 감소시킨다.
		public void removeStock(int stockNumber) {
			int restStock = this.stockNumber - stockNumber; //남은 재고 수량
			
			if(restStock < 0) {
				throw new OutOfStockException ("상품의 재고가 부족합니다." + "현재 재고수량: " + this.stockNumber);
			}
			this.stockNumber = restStock; //남은 재고수량 반영
		}
		
		//재고를 증가시킨다.
		public void addStock(int stockNumber) {
			this.stockNumber += stockNumber;
		}
}
