package com.niranjana.ecommerce.inventory.consumer.event;

import java.math.BigDecimal;

public class OrderItemEventDTO {

    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}
	public OrderItemEventDTO(Long productId, Integer quantity, BigDecimal price, BigDecimal subtotal) {
		super();
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
	}
	public OrderItemEventDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderItemEventDTO [productId=" + productId + ", quantity=" + quantity + ", price=" + price
				+ ", subtotal=" + subtotal + "]";
	}
    
    
}