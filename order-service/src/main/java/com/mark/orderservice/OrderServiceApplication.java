package com.mark.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.mark.orderservice.repository"})
@EntityScan(basePackages = {"com.mark.orderservice.entity"})
@SpringBootApplication(scanBasePackages = {"com.mark.orderservice", "com.mark.productservice", "com.mark.userservice"})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
