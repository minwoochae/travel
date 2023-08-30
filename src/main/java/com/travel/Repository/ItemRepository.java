package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.constant.ItemSellStatus;
import com.travel.entity.Item;


public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom{
	
	List<Item> findByItemNm(String itemNm); 
	
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);

	List<Item> findByIdIn(List<Long> asList);
	
}
