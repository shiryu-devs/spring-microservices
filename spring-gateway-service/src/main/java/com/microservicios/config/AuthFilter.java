package com.microservicios.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.microservicios.dto.RequestDto;
import com.microservicios.dto.TokenDto;

import io.netty.handler.codec.http2.Http2Headers;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config>{

	
	private WebClient.Builder webClient;

	
	public static class Config {
		
	}

	public AuthFilter(WebClient.Builder webClient) {
		super(Config.class);
		this.webClient=webClient;
	}

	public Mono<Void> onError(ServerWebExchange exchange,HttpStatus status){
		ServerHttpResponse response=exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		return (((exchange,chain)->{
			System.out.println("Entrada por aqui");
			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, HttpStatus.BAD_REQUEST);
			}
			String tokenHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String [] chunks=tokenHeader.split(" ");
			
			if (chunks.length!=2 || !chunks[0].equals("Bearer")){
				return onError(exchange, HttpStatus.BAD_REQUEST);
			}
			
			return webClient.build()
					.post()
					.uri("http://auth-service/auth/validate?token=" + chunks[1])
					.bodyValue(new RequestDto(exchange.getRequest().getPath().toString(), exchange.getRequest().getMethod().toString()))
					.retrieve()
					.bodyToMono(TokenDto.class)
					.map(t-> {
							t.getToken();
							return exchange;
					}).flatMap(chain::filter);
					
			
		}));
	}
}
