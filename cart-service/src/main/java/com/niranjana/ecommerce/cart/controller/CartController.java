package com.niranjana.ecommerce.cart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niranjana.ecommerce.cart.dto.CartResponse;
import com.niranjana.ecommerce.cart.entity.CartItem;
import com.niranjana.ecommerce.cart.service.CartService;
import com.niranjana.ecommerce.cart.util.LogUtil;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	@Autowired
	private CartService cartService;
	

	@PostMapping("/add")
	public ResponseEntity<CartResponse> addToCart(@RequestParam Long userId,@RequestParam Long productId,@RequestParam int qty) {
		log.info("Inside CartController - addToCart method, userId={}, productId={}, qty={}", userId, productId, qty);
	    CartResponse response = cartService.addToCart(userId, productId, qty);
	    log.info("Response → {}", LogUtil.toJson(response));
	    if (response.isSuccess()) {
	        return ResponseEntity.ok(response);
	    } else {
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	
	 // Remove single item from cart
	  @DeleteMapping("/remove")
	  public ResponseEntity<String> removeItem(@RequestParam Long userId,@RequestParam Long productId) {
		  log.info("Inside CartController - removeItem method, Remove item userId={}, productId={}", userId, productId);
		  
	        cartService.deleteItemFromCart(userId, productId);
	        return ResponseEntity.ok("Item removed successfully");
	    }

	    // Get cart by user
	    @GetMapping("/{userId}")
	    public ResponseEntity<List<CartItem>> getCart(@PathVariable("userId") Long userId) {
	        log.info("Inside CartController - getCart method, Get cart userId={}", userId);
	        List<CartItem> cartItems = cartService.getCartById(userId);
	        return ResponseEntity.ok(cartItems);
	    }
	    
	 // Get All cart ite, by userid
	    @GetMapping("/all-item")
	    public ResponseEntity<List<CartItem>> getAllCartItems() {
	    	log.info("Inside CartController - getAllCartItems");
	        List<CartItem> cartItems = cartService.getCartAllItem();
	        return ResponseEntity.ok(cartItems);
	    }

	    //Clear cart after order checkout
	    @DeleteMapping("/clear/{userId}")
	    public ResponseEntity<String> clearCart(@PathVariable("userId") Long userId) {
	    	log.info("Inside CartController - clearCart,Clear cart userId={}", userId);
	        cartService.clearCart(userId);
	        return ResponseEntity.ok("Cart cleared successfully");
	    }
}
