package com.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigServiceApplication.class, args);
	}

}
