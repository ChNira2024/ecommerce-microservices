package com.niranjana.ecommerce.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.niranjana.ecommerce.cart.dto.UserResponse;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("api/users/{id}")
    UserResponse getUser(@PathVariable("id") Long userId);
}
