package com.niranjana.ecommerce.notification.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.notification.consumer.event.PaymentEvent;

@Service
public class NotificationConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    // Payment Success
    @KafkaListener(topics = "${kafka.topic.payment-success}", groupId = "notification-group")
    public void handlePaymentSuccess(PaymentEvent event) {

        log.info("Payment SUCCESS received for orderId={}", event.getOrderId());

        // Simulate Email/SMS
        log.info("Sending SUCCESS notification to user for orderId={}", event.getOrderId());
    }

    //Payment Failed
    @KafkaListener(topics = "${kafka.topic.payment-failed}", groupId = "notification-group")
    public void handlePaymentFailure(PaymentEvent event) {

        log.warn("Payment FAILED for orderId={}", event.getOrderId());

        // Simulate Email/SMS
        log.info("Sending FAILURE notification to user for orderId={}", event.getOrderId());
    }
}