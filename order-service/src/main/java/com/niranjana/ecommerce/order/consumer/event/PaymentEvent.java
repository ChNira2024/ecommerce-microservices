package com.niranjana.ecommerce.order.consumer.event;

public class PaymentEvent {
    private Long orderId;
    private String status;
    
    public PaymentEvent(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "PaymentEvent [orderId=" + orderId + ", status=" + status + "]";
	}

}