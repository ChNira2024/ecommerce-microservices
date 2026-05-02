package com.niranjana.ecommerce.cart.dto;

public class CartResponse {

    private boolean success;
    private String message;
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
	public CartResponse(boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	public CartResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}