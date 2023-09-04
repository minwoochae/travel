package com.travel.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.entity.Cart;
import com.travel.entity.CartItem;
import com.travel.entity.Item;
import com.travel.entity.ItemImg;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	CartItem findByCartIdAndItemId(Long cartId, Long itemId);
	
	List<CartItem> findByIdIn(List<Long> ids);
	
    @Query("SELECT COUNT(ci) > 0 FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.item.id = :itemId")
    boolean existsCartItemByCartIdAndItemId(@Param("cartId") Long cartId, @Param("itemId") Long itemId);

    CartItem findByCartAndItem(Cart cart, Item item);

}
