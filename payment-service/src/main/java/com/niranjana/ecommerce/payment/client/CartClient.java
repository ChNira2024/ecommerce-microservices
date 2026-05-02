package com.niranjana.ecommerce.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cart-service")
public interface CartClient {

    @DeleteMapping("/api/cart/clear/{userId}")
    void clearCart(@PathVariable Long userId);
}