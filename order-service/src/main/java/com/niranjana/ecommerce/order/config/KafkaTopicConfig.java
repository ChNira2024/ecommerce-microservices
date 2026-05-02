package com.niranjana.ecommerce.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.order-created}")
    private String orderTopic;

    @Bean
    public NewTopic orderCreatedTopic() {
        return new NewTopic(orderTopic, 3, (short) 1);
    }
}
