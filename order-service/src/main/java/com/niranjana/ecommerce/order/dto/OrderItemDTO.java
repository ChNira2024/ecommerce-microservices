package com.niranjana.ecommerce.order.dto;

import java.math.BigDecimal;

public class OrderItemDTO {

	private Long id;
	private Long productId;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal subtotal;//not storing in db, only showing to user after order placed
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	
	public OrderItemDTO(Long id, Long productId, Integer quantity, BigDecimal price, BigDecimal subtotal) {
		super();
		this.id = id;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
	}
	public OrderItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderItemDTO [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price
				+ ", subtotal=" + subtotal + "]";
	}
	
	
	
}	