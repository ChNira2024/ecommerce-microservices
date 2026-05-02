package com.niranjana.ecommerce.order.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItem {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private Long productId;
	    private Integer quantity;
	    private BigDecimal price;

	    @ManyToOne
	    @JoinColumn(name = "order_id", nullable = false)
	    private Order order;

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

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		public OrderItem(Long id, Long productId, Integer quantity, BigDecimal price, Order order) {
			super();
			this.id = id;
			this.productId = productId;
			this.quantity = quantity;
			this.price = price;
			this.order = order;
		}

		public OrderItem() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "OrderItem [id=" + id + ", productId=" + productId + ", quantity=" + quantity + ", price=" + price
					+ ", order=" + order + "]";
		}
	    
	    
}
