package com.niranjana.ecommerce.order.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.order.producer.event.OrderEvent;

@Service
public class OrderProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${kafka.topic.order-created}")
    private String orderTopic;

    public void sendOrderEvent(OrderEvent event) {

        log.info("Sending Order Event to Kafka: {}", event);

        kafkaTemplate.send(orderTopic, event.getOrderId().toString(), event);

        log.info("Order Event sent successfully for orderId={}", event.getOrderId());
    }
}