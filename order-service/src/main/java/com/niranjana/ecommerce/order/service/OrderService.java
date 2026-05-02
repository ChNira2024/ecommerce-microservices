package com.niranjana.ecommerce.order.service;
import java.util.List;
import com.niranjana.ecommerce.order.dto.OrderResponse;

public interface OrderService {

	public OrderResponse createOrder(Long userId);
	public OrderResponse getOrderById(Long orderId);
	public List<OrderResponse> getOrdersByUser(Long userId);
	public void updateOrderStatus(Long orderId, String status);
	
}
