package com.niranjana.ecommerce.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.niranjana.ecommerce.order.entity.CartItem;


@FeignClient(name="cart-service")
public interface CartServiceClient {
	
	@GetMapping("/api/cart/{userId}")
	List<CartItem> getCart(@PathVariable("userId") Long userId);
	
	@DeleteMapping("api/cart/clear/{userId}")
    public String clearCart(@PathVariable("userId") Long userId);

}
