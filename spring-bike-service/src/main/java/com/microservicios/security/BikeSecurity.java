package com.microservicios.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

public class BikeSecurity {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
        	.anyRequest()
        	.authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt();
        
        return http.build();
    }
}
