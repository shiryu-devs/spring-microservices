package com.microservicios.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicios.entity.User;
import com.microservicios.model.Bike;
import com.microservicios.model.Car;
import com.microservicios.service.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User>users=this.userService.getAll();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id){
		User user=this.userService.getUserById(id);
		if (user==null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user){
		User userNew=this.userService.save(user);
		
		return ResponseEntity.ok(userNew);
	}
	
	@CircuitBreaker(name="carsCB", fallbackMethod = "fallBackGetCars")
	@GetMapping("/{id}/cars")
	public ResponseEntity<List<Car>> getCarsByUserId(@PathVariable("id") int userId){
		List<Car> cars=this.userService.getCars(userId);
		if (cars.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(cars);
	}
	@CircuitBreaker(name="bikesCB", fallbackMethod = "fallBackGetBikes")
	@GetMapping("/{id}/bikes")
	public ResponseEntity<List<Bike>> getBikesByUserId(@PathVariable("id") int userId){
		List<Bike> bikes=this.userService.getBikes(userId);
		if (bikes.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(bikes);
	}
	
	@CircuitBreaker(name="carsCB", fallbackMethod = "fallBackSaveCar")
	@PostMapping("/{id}/cars")
	public ResponseEntity<Car> saveCar( @PathVariable("id")int userId,@RequestBody Car car){
		if (this.userService.getUserById(userId)==null) {
			return ResponseEntity.notFound().build();
		}
		Car carNew=this.userService.saveCar(userId,car);
		return ResponseEntity.ok(carNew);
		
	}
	
	@CircuitBreaker(name="bikesCB", fallbackMethod = "fallBackSaveBike")
	@PostMapping("/{id}/bikes")
	public ResponseEntity<Bike> saveBike( @PathVariable("id")int userId,@RequestBody Bike bike){
		if (this.userService.getUserById(userId)==null) {
			return ResponseEntity.notFound().build();
		}
		Bike bikeNew=this.userService.saveBike(userId,bike);
		return ResponseEntity.ok(bikeNew);
	}
	
	@CircuitBreaker(name="allCB", fallbackMethod = "fallBackGetAllVehicles")
	@GetMapping("/{id}/all")
	public ResponseEntity<Map<String,Object>> getAllVehicles(@PathVariable("id")int userId){
		Map<String,Object> userVehicules=this.userService.getUserAndVehicles(userId);
		if (userVehicules.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(userVehicules);
	}
	
	private  ResponseEntity<List<Car>> fallBackGetCars(@PathVariable("id") int userId){
		return new ResponseEntity("El usuario "+userId+" tiene los coches en el taller ", HttpStatus.OK);
	}
	
	private ResponseEntity<Car> fallBackSaveCar( @PathVariable("id")int userId,@RequestBody Car car, RuntimeException e){
		return new ResponseEntity("El usuario "+userId+" no tiene dinero para coches ", HttpStatus.OK);
	}
	
	private ResponseEntity<List<Bike>> fallBackGetBikes(@PathVariable("id") int userId){
		return new ResponseEntity("El usuario "+userId+" tiene las motos en el taller ", HttpStatus.OK);
	}
	
	private ResponseEntity<Bike> fallBackSaveBike( @PathVariable("id")int userId,@RequestBody Bike bike,RuntimeException e){
		return new ResponseEntity("El usuario "+userId+" no tiene dinero para motos ", HttpStatus.OK);
	}
	
	private ResponseEntity<Map<String,Object>> fallBackGetAllVehicles(@PathVariable("id")int userId){
		return new ResponseEntity("El usuario "+userId+" tiene los vehiculos en el taller ", HttpStatus.OK);
	}

}
