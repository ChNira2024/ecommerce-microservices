package com.niranjana.ecommerce.payment.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.payment.client.CartClient;
import com.niranjana.ecommerce.payment.client.NotificationClient;
import com.niranjana.ecommerce.payment.client.OrderClient;
import com.niranjana.ecommerce.payment.dto.PaymentRequest;
import com.niranjana.ecommerce.payment.dto.PaymentResponse;
import com.niranjana.ecommerce.payment.service.PaymentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class PaymentServiceImpl implements PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired private OrderClient orderClient;
    @Autowired private CartClient cartClient;
    @Autowired private NotificationClient notificationClient;

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for orderId={}, userId={}",request.getOrderId(), request.getUserId());
        boolean success = simulatePayment();

        if (success) {
            log.info("Payment SUCCESS for orderId={}", request.getOrderId());

            //Update order
            orderClient.updateOrderStatus(request.getOrderId(), "CONFIRMED");

            // Clear cart
            cartClient.clearCart(request.getUserId());

            // Notify user
            notificationClient.sendNotification(request.getUserId(),"Your order is confirmed!");
            return new PaymentResponse(true, "Payment successful");
        } else {
            log.warn("Payment FAILED for orderId={}", request.getOrderId());
            orderClient.updateOrderStatus(request.getOrderId(), "FAILED");
            notificationClient.sendNotification(request.getUserId(),"Payment failed. Please retry.");
            return new PaymentResponse(false, "Payment failed");
        }
    }

    public PaymentResponse paymentFallback(PaymentRequest request, Throwable ex) {
        log.error("Payment service unavailable", ex);
        return new PaymentResponse(false, "Payment service unavailable");
    }

    private boolean simulatePayment() {
        return Math.random() > 0.2; // 80% success
    }
}
