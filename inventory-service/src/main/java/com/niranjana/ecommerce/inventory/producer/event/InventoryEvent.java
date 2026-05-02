package com.niranjana.ecommerce.inventory.producer.event;

public class InventoryEvent {

    private Long orderId;
    private String status; // SUCCESS / FAILED
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
	public InventoryEvent(Long orderId, String status) {
		super();
		this.orderId = orderId;
		this.status = status;
	}
	public InventoryEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "InventoryEvent [orderId=" + orderId + ", status=" + status + "]";
	}
    
    
}