package com.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAuthServiceApplication.class, args);
	}

}
