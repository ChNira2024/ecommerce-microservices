package com.niranjana.ecommerce.order.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.niranjana.ecommerce.order.dto.OrderResponse;
import com.niranjana.ecommerce.order.service.OrderService;


@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired private OrderService orderService;


	@PostMapping("/create/{userId}")
	public ResponseEntity<OrderResponse> createOrder(@PathVariable Long userId) {
	    log.info("Create Order API called, userId={}", userId);

	    if (userId == null || userId <= 0) {
	        return ResponseEntity.badRequest()
	                .body(new OrderResponse(false, "Invalid userId"));
	    }
	    OrderResponse response = orderService.createOrder(userId);
	    if (!response.isSuccess()) {
	        return ResponseEntity.status(503).body(response);
	    }
	    return ResponseEntity.status(201).body(response);
	}
    
    // Get single order by using userId
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable("orderId") Long orderId) {

        log.info("Inside OrderController - getOrderById, orderId={}", orderId);

        OrderResponse response = orderService.getOrderById(orderId);

        return ResponseEntity.ok(response);
    }

    // Get all Orders by using UserId
    @GetMapping("/alluser/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable("userId") Long userId) {

        log.info("Fetching orders for userId={}", userId);

        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId,@RequestParam String status) {
        log.info("Updating order status, orderId={}, status={}", orderId, status);
        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated successfully");
    }
}