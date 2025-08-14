package com.example.idelivery.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class IDeliveryServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(IDeliveryServiceRegistryApplication.class, args);
	}

}
