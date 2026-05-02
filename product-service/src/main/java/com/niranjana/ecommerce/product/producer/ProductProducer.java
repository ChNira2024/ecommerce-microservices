package com.niranjana.ecommerce.product.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.product.producer.event.ProductEvent;

@Service
public class ProductProducer {

    private static final Logger log = LoggerFactory.getLogger(ProductProducer.class);

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    @Value("${kafka.topic.product-created}")
    private String topic;

    public void sendProductCreatedEvent(ProductEvent event) {

    	log.info("Sending event to Kafka: productId={}, stock={}", event.getProductId(), event.getStockQuantity());
        log.info("Sending event to Kafka: {}", event);

        kafkaTemplate.send(topic, event.getProductId().toString(), event);
    }
}