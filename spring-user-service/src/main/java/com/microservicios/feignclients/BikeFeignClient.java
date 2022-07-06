package com.microservicios.feignclients;

import java.util.List;

import javax.naming.NotContextException;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.microservicios.model.Bike;

@FeignClient(name = "bike-service")
public interface BikeFeignClient {
	@PostMapping("/bikes")
	Bike save(@RequestBody Bike bike);
	
	@GetMapping("/bikes/users/{id}")
	public List<Bike> getByUserId(@PathVariable("id") int userId)throws NotContextException;
}
