package com.niranjana.ecommerce.payment.service;

import com.niranjana.ecommerce.payment.dto.PaymentRequest;
import com.niranjana.ecommerce.payment.dto.PaymentResponse;

public interface PaymentService {
	
	public PaymentResponse processPayment(PaymentRequest request);

}
