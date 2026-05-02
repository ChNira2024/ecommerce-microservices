package com.niranjana.ecommerce.payment.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.payment.consumer.event.InventoryEvent;
import com.niranjana.ecommerce.payment.producer.PaymentProducer;
import com.niranjana.ecommerce.payment.producer.event.PaymentEvent;

@Service
public class InventoryEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(InventoryEventConsumer.class);

    @Autowired
    private PaymentProducer paymentProducer;

    // Listen SUCCESS from Inventory
    @KafkaListener(topics = "${kafka.topic.inventory-success}", groupId = "payment-group")
    public void handleInventorySuccess(InventoryEvent event) {

        log.info("Inventory SUCCESS for orderId={}", event.getOrderId());

        try {
            // simulate payment
            log.info("Processing payment for orderId={}", event.getOrderId());

            // If payment SUCCESS
            paymentProducer.sendSuccess(new PaymentEvent(event.getOrderId(), "SUCCESS"));

        } catch (Exception ex) {
            log.error("Payment failed", ex);
            //IF Payment fail
            paymentProducer.sendFailure(new PaymentEvent(event.getOrderId(), "FAILED"));
        }
    }

    // Listen FAILURE from Inventory
    @KafkaListener(topics = "${kafka.topic.inventory-failed}", groupId = "payment-group")
    public void handleInventoryFailure(InventoryEvent event) {

        log.warn("Skipping payment, inventory FAILED for orderId={}", event.getOrderId());

        paymentProducer.sendFailure(new PaymentEvent(event.getOrderId(), "FAILED"));
    }
}