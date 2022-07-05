package com.microservicios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.Car;
import com.microservicios.service.CarService;

@RestController
@RequestMapping("cars")
public class CarController {
	@Autowired
	private CarService carService;
	
	@GetMapping
	public ResponseEntity<List<Car>> getAll(){
		List<Car> cars=this.carService.getAll();
		if(cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cars);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Car> getById(@PathVariable("id") int id){
		Car car=this.carService.getCarById(id);
		if(car==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(car);
	}
	
	@PostMapping
	public ResponseEntity<Car> save(@RequestBody Car car){
		Car carNew=this.carService.save(car);
		return ResponseEntity.ok(carNew);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<List<Car>> getByUserId(@PathVariable("id") int userId){
		List<Car> cars=this.carService.getCarByUserId(userId);
		if(cars.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(cars);
	}
}

