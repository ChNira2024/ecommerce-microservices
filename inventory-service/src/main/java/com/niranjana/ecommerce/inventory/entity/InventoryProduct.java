package com.niranjana.ecommerce.inventory.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "inventory_products")
public class InventoryProduct {

    @Id
    private Long productId;   // same ID as product-service

    private Integer stockQuantity;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public InventoryProduct(Long productId, Integer stockQuantity, LocalDateTime updatedAt) {
		super();
		this.productId = productId;
		this.stockQuantity = stockQuantity;
		this.updatedAt = updatedAt;
	}

	public InventoryProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "InventoryProduct [productId=" + productId + ", stockQuantity=" + stockQuantity + ", updatedAt="
				+ updatedAt + "]";
	}
    
    
}