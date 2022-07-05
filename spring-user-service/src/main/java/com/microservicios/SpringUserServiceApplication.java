package com.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringUserServiceApplication.class, args);
	}

}
