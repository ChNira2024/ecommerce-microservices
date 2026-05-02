package com.niranjana.ecommerce.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.niranjana.ecommerce.cart.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	List<CartItem> findByUserId(Long userId);
	
    CartItem findByUserIdAndProductId(Long userId, Long productId);

    void deleteByUserIdAndProductId(Long userId, Long productId);

    

    void deleteByUserId(Long userId);

	List<CartItem> findAll();
	
}
