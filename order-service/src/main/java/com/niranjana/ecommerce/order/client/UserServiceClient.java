package com.niranjana.ecommerce.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.niranjana.ecommerce.order.dto.UserResponse;


@FeignClient(name="user-service")
public interface UserServiceClient {
	
	@GetMapping("api/users/{id}")
    public UserResponse getUser(@PathVariable("id") Long id);

}
