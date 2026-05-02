package com.niranjana.ecommerce.inventory.consumer.event;

import java.math.BigDecimal;
import java.util.List;

public class OrderEvent {
	private Long orderId;
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemEventDTO> items;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<OrderItemEventDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemEventDTO> items) {
		this.items = items;
	}
	public OrderEvent(Long orderId, Long userId, BigDecimal totalAmount, String status, List<OrderItemEventDTO> items) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.items = items;
	}
	public OrderEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderEvent [orderId=" + orderId + ", userId=" + userId + ", totalAmount=" + totalAmount + ", status="
				+ status + ", items=" + items + "]";
	}
    
	
    
}