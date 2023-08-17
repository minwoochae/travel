package com.travel.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.travel.Dto.ItemSearchDto;
import com.travel.Dto.MainItemDto;
import com.travel.entity.Item;


public interface ItemRepositoryCustom {
	Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

	Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
