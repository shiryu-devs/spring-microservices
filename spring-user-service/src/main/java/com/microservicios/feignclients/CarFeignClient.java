package com.microservicios.feignclients;

import java.util.List;

import javax.naming.NotContextException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicios.model.Car;

@FeignClient(name="car-service")

public interface CarFeignClient {
	@PostMapping("/cars")
	Car save(@RequestBody Car car);
	
	@GetMapping("/cars/users/{id}")
	public List<Car> getByUserId(@PathVariable("id") int userId) throws NotContextException;
	
}
