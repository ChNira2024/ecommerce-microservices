package com.niranjana.ecommerce.order.service.impl;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niranjana.ecommerce.order.client.CartServiceClient;
import com.niranjana.ecommerce.order.client.PaymentClient;
import com.niranjana.ecommerce.order.client.UserServiceClient;
import com.niranjana.ecommerce.order.dto.OrderItemDTO;
import com.niranjana.ecommerce.order.dto.OrderResponse;
import com.niranjana.ecommerce.order.dto.PaymentRequest;
import com.niranjana.ecommerce.order.dto.UserResponse;
import com.niranjana.ecommerce.order.entity.CartItem;
import com.niranjana.ecommerce.order.entity.Order;
import com.niranjana.ecommerce.order.entity.OrderItem;
import com.niranjana.ecommerce.order.enums.OrderStatus;
import com.niranjana.ecommerce.order.exception.OrderException;
import com.niranjana.ecommerce.order.producer.OrderProducer;
import com.niranjana.ecommerce.order.producer.event.OrderEvent;
import com.niranjana.ecommerce.order.producer.event.OrderItemEventDTO;
import com.niranjana.ecommerce.order.repository.OrderRepository;
import com.niranjana.ecommerce.order.service.OrderService;
import com.niranjana.ecommerce.order.util.LogUtil;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired private CartServiceClient cartServiceClient;
	@Autowired private UserServiceClient userServiceClient;
	@Autowired private PaymentClient paymentClient;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderProducer orderProducer;
	

	@Transactional
	@Override
	@CircuitBreaker(name = "orderService", fallbackMethod = "orderFallback")
	public OrderResponse createOrder(Long userId) {
		log.info("Inside OrderServiceImpl - createOrder method, userId={}",userId);
		
		  // validate user(call external user-service)
		UserResponse user;
        try {
            user = userServiceClient.getUser(userId);
            log.debug("UserResponse: {}", LogUtil.toJson(user));
        } catch (FeignException.NotFound ex) {
            log.warn("User not found: {}", userId);
            throw new OrderException("User not found");
        } catch (Exception ex) {
            log.error("User service error", ex);
            throw new OrderException("User service unavailable");
        }
        // get cart(call external cart-service)
        List<CartItem> cartItems;
        try {
            cartItems = cartServiceClient.getCart(userId);
            log.debug("CartItem: {}", LogUtil.toJson(cartItems));
        } catch (Exception ex) {
            log.error("Cart service error", ex);
            throw new OrderException("Cart service unavailable");
        }

        if (cartItems == null || cartItems.isEmpty()) {
            throw new OrderException("Cart is empty");
        }

        // calculate total price
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order
        Order order = new Order();
        order.setUserId(user.getId());   
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> {
                    OrderItem oi = new OrderItem();
                    oi.setProductId(item.getProductId());
                    oi.setQuantity(item.getQuantity());
                    oi.setPrice(item.getPrice());
                    oi.setOrder(order);
                    return oi;
                }).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        log.info("savedOrder: {}", LogUtil.toJson(savedOrder));
        log.info("Order created with ID={}", savedOrder.getId());
        
        List<OrderItemEventDTO> itemDTOs = cartItems.stream()
                .map(item -> new OrderItemEventDTO(
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                ))
                .toList();
        
        //create event
        OrderEvent event = new OrderEvent(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus().name(),
                itemDTOs
        );
        log.info("Created OrderEvent: {}", event);
        // send to kafka
        orderProducer.sendOrderEvent(event);
        
		/*
		 * // Call Payment Service try { paymentClient.processPayment(new
		 * PaymentRequest(savedOrder.getId(), userId, totalPrice));
		 * log.info("Payment triggered for orderId={}", savedOrder.getId()); } catch
		 * (Exception ex) { log.error("Payment service failed", ex); throw new
		 * OrderException("Payment failed, please retry"); }
		 * 
		 * // ---------------- CLEAR CART ----------------
		 * cartServiceClient.clearCart(userId); log.info("Cart cleared for userId={}",
		 * userId);
		 */

        return mapToOrderResponse(savedOrder);
    }

    // fallback method
	public OrderResponse orderFallback(Long userId, Throwable ex) {
        log.error("Circuit breaker triggered", ex);
        return new OrderResponse(false, "Order service temporarily unavailable");
    }

    // get single order by using orderId
    @Override
    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapToOrderResponse(order);
    }

    //get list of order by uding userId
    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId).stream().map(this::mapToOrderResponse).toList();
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId)
                					 .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(orderStatus);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid order status: " + status);
        }
        orderRepository.save(order);
        log.info("Order status updated successfully, orderId={}, newStatus={}", orderId, status);
    }
    
    // ---------------- MAPPER ----------------
    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemDTO> items = order.getItems().stream()
                .map(orderItem -> new OrderItemDTO(
                        orderItem.getId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getPrice() // or quantity * price if needed
                ))
                .toList();

        return new OrderResponse(
                true,
                "Order created successfully",
                order.getId(),
                order.getUserId(), 
                order.getTotalAmount(),
                order.getStatus(),
                items,
                order.getCreatedAt()
        );
    }
}
