package com.niranjana.ecommerce.cart.service;

import java.util.List;

import com.niranjana.ecommerce.cart.dto.CartResponse;
import com.niranjana.ecommerce.cart.entity.CartItem;

public interface CartService {

	public CartResponse addToCart(Long userId,Long productId, int qty);

	public void deleteItemFromCart(Long userId, Long productId);

	public List<CartItem> getCartById(Long userId);
	
	public List<CartItem> getCartAllItem();

	public void clearCart(Long userId);
}
