package com.niranjana.ecommerce.order.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.order.consumer.event.PaymentEvent;
import com.niranjana.ecommerce.order.entity.Order;
import com.niranjana.ecommerce.order.enums.OrderStatus;
import com.niranjana.ecommerce.order.repository.OrderRepository;

@Service
public class PaymentEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);

    @Autowired
    private OrderRepository orderRepository;

    @KafkaListener(topics = "payment-success", groupId = "order-group")
    public void handlePaymentSuccess(PaymentEvent event) {

        log.info("Payment SUCCESS received for orderId={}", event.getOrderId());

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);

        log.info("Order marked CONFIRMED for orderId={}", event.getOrderId());
    }

    @KafkaListener(topics = "payment-failed", groupId = "order-group")
    public void handlePaymentFailure(PaymentEvent event) {

        log.warn("Payment FAILED for orderId={}", event.getOrderId());

        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);

        log.info("Order marked CANCELLED for orderId={}", event.getOrderId());
    }
}