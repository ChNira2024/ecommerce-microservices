package com.niranjana.ecommerce.payment.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.payment.producer.event.PaymentEvent;

@Service
public class PaymentProducer {

    @Autowired
    private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @Value("${kafka.topic.payment-success}")
    private String successTopic;

    @Value("${kafka.topic.payment-failed}")
    private String failedTopic;

    public void sendSuccess(PaymentEvent event) {
        kafkaTemplate.send(successTopic, event.getOrderId().toString(), event);
    }

    public void sendFailure(PaymentEvent event) {
        kafkaTemplate.send(failedTopic, event.getOrderId().toString(), event);
    }
}