package com.microservicios.feignclients;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate template) {
		Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		template.header("Authorization", "Bearer "+jwt.getTokenValue());
		
	}

	
}
