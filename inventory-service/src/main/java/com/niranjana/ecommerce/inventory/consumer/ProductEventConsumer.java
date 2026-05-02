package com.niranjana.ecommerce.inventory.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.niranjana.ecommerce.inventory.consumer.event.OrderEvent;
import com.niranjana.ecommerce.inventory.consumer.event.OrderItemEventDTO;
import com.niranjana.ecommerce.inventory.consumer.event.ProductEvent;
import com.niranjana.ecommerce.inventory.entity.InventoryProduct;
import com.niranjana.ecommerce.inventory.producer.InventoryProducer;
import com.niranjana.ecommerce.inventory.producer.event.InventoryEvent;
import com.niranjana.ecommerce.inventory.repo.InventoryRepository;

@Service
public class ProductEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumer.class);

    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private InventoryProducer inventoryProducer;

    @Value("${kafka.topic.inventory-success:inventory-success}")
    private String successTopic;

    @Value("${kafka.topic.inventory-failed:inventory-failed}")
    private String failedTopic;
 
    @KafkaListener(topics = "product-topic-microservices-producer", groupId = "inventory-group")
    public void consumeProduct(ProductEvent event) {
        log.info("Received product-created event: productId={}", event.getProductId());
        try {
            InventoryProduct product = new InventoryProduct();
            product.setProductId(event.getProductId());
            product.setStockQuantity(event.getStockQuantity());
            inventoryRepository.save(product);
            log.info("Inventory created for productId={}", event.getProductId());
        } catch (Exception ex) {
            log.error("Error saving inventory product", ex);
        }
    }
    
    @KafkaListener(topics = "order-topic-microservices-producer", groupId = "inventory-group")
    public void consumeOrder(OrderEvent event) {
        log.info("Inventory received order event: {}", event.getOrderId());
        try {
            // Check stock
            for (OrderItemEventDTO item : event.getItems()) {

                InventoryProduct product = inventoryRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found in inventory"));

                if (product.getStockQuantity() < item.getQuantity()) {
                    log.warn("Stock insufficient for productId={}", item.getProductId());
                    inventoryProducer.sendFailure(failedTopic,new InventoryEvent(event.getOrderId(), "FAILED"));
                    return;
                }
            }

            // Deduct stock
            for (OrderItemEventDTO item : event.getItems()) {
                InventoryProduct product = inventoryRepository.findById(item.getProductId()).get();
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                inventoryRepository.save(product);
            }

            // Send success
            inventoryProducer.sendSuccess(successTopic,new InventoryEvent(event.getOrderId(), "SUCCESS"));
            log.info("Inventory updated successfully for orderId={}", event.getOrderId());

        } catch (Exception ex) {
            log.error("Inventory processing failed", ex);
            inventoryProducer.sendSuccess(failedTopic,new InventoryEvent(event.getOrderId(), "FAILED"));
        }
    }
}