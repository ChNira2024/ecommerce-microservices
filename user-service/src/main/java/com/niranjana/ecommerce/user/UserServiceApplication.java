package com.niranjana.ecommerce.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {

	private static final Logger log = LoggerFactory.getLogger(UserServiceApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
		log.info("UserServiceApplication is started.....");
	}

}
