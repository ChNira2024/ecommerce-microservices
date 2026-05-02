package com.niranjana.ecommerce.product.producer.event;

public class ProductEvent {

    private Long productId;
    private Integer stockQuantity;

    public ProductEvent(Long productId, Integer stockQuantity) {
        this.productId = productId;
        this.stockQuantity = stockQuantity;
    }

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

	public ProductEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ProductEvent [productId=" + productId + ", stockQuantity=" + stockQuantity + "]";
	}

   
    
}