package com.niranjana.ecommerce.cart.service.impl;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.cart.client.ProductClient;
import com.niranjana.ecommerce.cart.client.UserClient;
import com.niranjana.ecommerce.cart.dto.CartResponse;
import com.niranjana.ecommerce.cart.dto.ProductResponse;
import com.niranjana.ecommerce.cart.dto.UserResponse;
import com.niranjana.ecommerce.cart.entity.CartItem;
import com.niranjana.ecommerce.cart.exception.CartException;
import com.niranjana.ecommerce.cart.exception.CartItemNotFoundException;
import com.niranjana.ecommerce.cart.repository.CartItemRepository;
import com.niranjana.ecommerce.cart.service.CartService;
import com.niranjana.ecommerce.cart.util.LogUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;

@Service
public class CartServiceImpl  implements CartService{
	private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
	
	@Autowired private CartItemRepository cartItemRepository;	
	 @Autowired private ProductClient productClient;
	 @Autowired private UserClient userClient;	

	 @CircuitBreaker(name = "cartService", fallbackMethod = "cartFallback")
	 @Override
	 public CartResponse addToCart(Long userId, Long productId, int qty) {
		 log.info("Inside CartServiceImpl - addToCart method, userId={}, productId={}, qty={}",userId, productId, qty);
		 
		 log.info("Add to cart: userId={}, productId={}, qty={}", userId, productId, qty);

	        // 1. Product validation
	        ProductResponse product;
	        try {
	            product = productClient.getProductById(productId);
	            log.debug("Product response: {}", LogUtil.toJson(product));
	        } catch (feign.FeignException.NotFound ex) {
	            log.warn("Product not found, id={}", productId);
	            throw new CartException("Product not found");
	        } catch (Exception ex) {
	            log.error("Product service error", ex);
	            throw new CartException("Product service unavailable");
	        }

	        if (product.getStockQuantity() < qty) {
	            throw new CartException("Insufficient stock");
	        }
		 
	     if (product.getStockQuantity() < qty) {
	         return new CartResponse(false, "Insufficient stock");
	     }

	     // 2. User validation
	     try {
	            UserResponse user = userClient.getUser(userId);
	            log.debug("User response: {}", LogUtil.toJson(user));
	        } catch (feign.FeignException.NotFound ex) {
	            log.warn("User not found, id={}", userId);
	            throw new CartException("User not found");
	        } catch (Exception ex) {
	            log.error("User service error", ex);
	            throw new CartException("User service unavailable");
	        }
	     
	        BigDecimal price = product.getPrice();

	     // 3. Cart logic
	     CartItem existing = cartItemRepository.findByUserIdAndProductId(userId, productId);

	        if (existing != null) {
	            int newQty = existing.getQuantity() + qty;
	            existing.setQuantity(newQty);
	            existing.setPrice(price.multiply(BigDecimal.valueOf(newQty)));

	            cartItemRepository.save(existing);

	            log.info("Cart updated, userId={}, productId={}", userId, productId);
	            return new CartResponse(true, "Cart updated successfully");
	        }
	        CartItem cartItem = new CartItem();
	        cartItem.setUserId(userId);
	        cartItem.setProductId(productId);
	        cartItem.setQuantity(qty);
	        cartItem.setPrice(price.multiply(BigDecimal.valueOf(qty)));

	        cartItemRepository.save(cartItem);
	        log.info("Item added to cart, userId={}, productId={}", userId, productId);
	        return new CartResponse(true, "Item added to cart");
	 }
	 public CartResponse cartFallback(Long userId, Long productId, int qty, Throwable ex) {
	        log.error("Circuit breaker fallback triggered", ex);
	        return new CartResponse(false, "Cart service temporarily unavailable");
	    }

	@Transactional
	@Override
	public void deleteItemFromCart(Long userId, Long productId) {
        log.info("inside deleteItemFromCart, Deleting item: userId={}, productId={}", userId, productId);
        CartItem item = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if (item == null) {
            log.warn("Cart item not found");
            throw new CartItemNotFoundException("Cart item not found");
        }
        cartItemRepository.delete(item);
        log.info("Item deleted successfully");
	}
	
	@Override
	public List<CartItem> getCartById(Long userId) {
		 log.info("Inside getCartById, Fetching cart for userId={}", userId);

	        List<CartItem> items = cartItemRepository.findByUserId(userId);
	        if (items.isEmpty()) {
	            log.warn("Cart is empty for userId={}", userId);
	        }
	        return items;
	}
	@Override
	public List<CartItem> getCartAllItem() {
		log.info("Inside getCartAllItem ");
		return cartItemRepository.findAll();
	}

	@Transactional
	@Override
	 public void clearCart(Long userId) {
		log.info("Clearing cart for userId={}", userId);
        cartItemRepository.deleteByUserId(userId);
        log.info("Cart cleared successfully");
    }
}
