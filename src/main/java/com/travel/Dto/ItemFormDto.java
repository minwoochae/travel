package com.travel.Dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.travel.constant.ItemSellStatus;
import com.travel.entity.Item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemFormDto {

	private Long id;

	@NotBlank(message = "상품명은 필수 입력 값입니다.")
	private String itemNm;

	@NotNull(message = "가격은 필수 입력 값입니다.")
	private int price;

	@NotNull(message = "재고는 필수 입력 값입니다.")
	private int stockNumber;

	@NotBlank(message = "상품 상세설명은 필수 입력 값입니다.")
	private String itemDetail;

	private ItemSellStatus itemSellStatus;

	// 상품이미지 저장
	private List<ItemImgDto> itemImgDtoList = new ArrayList<>();

	// 상품이미지 아이디 저장
	private List<Long> itemImgIds = new ArrayList<>();

	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto > entity로 바꿈
	public Item createItem() {
		return modelMapper.map(this,Item.class);
	}
		
	// entity > dto로 바꿈.
	public static ItemFormDto of(Item item) {
		return modelMapper.map(item, ItemFormDto.class);
	}
	

}
