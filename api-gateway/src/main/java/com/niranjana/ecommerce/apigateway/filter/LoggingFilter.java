package com.niranjana.ecommerce.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                            org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        String requestId = UUID.randomUUID().toString();

        // Request details
        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        log.info("[{}] Incoming Request -> Method: {}, Path: {}", requestId, method, path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long duration = System.currentTimeMillis() - startTime;

                    log.info("[{}] Response -> Status: {}, Time: {} ms",
                            requestId,
                            exchange.getResponse().getStatusCode(),
                            duration);
                }));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

//“Logging filter in API Gateway is not mandatory, but it is commonly used in microservices to track incoming requests, 
//response status, and latency for debugging and monitoring purposes.”
