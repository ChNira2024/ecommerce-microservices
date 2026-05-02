package com.niranjana.ecommerce.inventory.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.inventory.producer.event.InventoryEvent;


@Service
public class InventoryProducer {

	@Autowired
    private KafkaTemplate<String, InventoryEvent> kafkaTemplate;

    @Value("${kafka.topic.inventory-success}")
    private String successTopic;

    @Value("${kafka.topic.inventory-failed}")
    private String failedTopic;

    public void sendSuccess(String topic,InventoryEvent event) {
        kafkaTemplate.send(successTopic, event.getOrderId().toString(), event);
    }

    public void sendFailure(String topic,InventoryEvent event) {
        kafkaTemplate.send(failedTopic, event.getOrderId().toString(), event);
    }
}