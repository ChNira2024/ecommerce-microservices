package com.niranjana.ecommerce.order.dto;

import java.math.BigDecimal;

public class PaymentResponse {
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public PaymentResponse(Long orderId, Long userId, BigDecimal amount) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
	}
	public PaymentResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "PaymentResponse [orderId=" + orderId + ", userId=" + userId + ", amount=" + amount + "]";
	}
    
    
}