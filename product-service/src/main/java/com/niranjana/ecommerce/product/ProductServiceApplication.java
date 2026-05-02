package com.niranjana.ecommerce.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {
	private static final Logger log = LoggerFactory.getLogger(ProductServiceApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
		log.info("ProductServiceApplication is started........");

	}

}
