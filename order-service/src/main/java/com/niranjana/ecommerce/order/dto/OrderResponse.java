package com.niranjana.ecommerce.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.niranjana.ecommerce.order.enums.OrderStatus;


public class OrderResponse {

	private boolean success;
    private String message;

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public List<OrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public OrderResponse(boolean success, String message, Long id, Long userId, BigDecimal totalAmount,
			OrderStatus status, List<OrderItemDTO> items, LocalDateTime createdAt) {
		super();
		this.success = success;
		this.message = message;
		this.id = id;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.status = status;
		this.items = items;
		this.createdAt = createdAt;
	}
	public OrderResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderResponse(boolean success, String message) {
	    this.success = success;
	    this.message = message;
	}
	@Override
	public String toString() {
		return "OrderResponse [success=" + success + ", message=" + message + ", id=" + id + ", userId=" + userId
				+ ", totalAmount=" + totalAmount + ", status=" + status + ", items=" + items + ", createdAt="
				+ createdAt + "]";
	}
    
}
