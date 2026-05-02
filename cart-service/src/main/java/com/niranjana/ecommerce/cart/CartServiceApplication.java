package com.niranjana.ecommerce.cart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CartServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(CartServiceApplication.class);

	public static void main(String[] args) {
		log.info("CartServiceApplication is started..");
		SpringApplication.run(CartServiceApplication.class, args);
	}

}
