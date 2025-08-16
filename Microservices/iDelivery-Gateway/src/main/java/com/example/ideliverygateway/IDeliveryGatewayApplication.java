package com.example.ideliverygateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IDeliveryGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(IDeliveryGatewayApplication.class, args);
	}

}
