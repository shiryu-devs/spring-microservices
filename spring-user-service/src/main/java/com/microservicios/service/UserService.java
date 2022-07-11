package com.microservicios.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NotContextException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.microservicios.entity.User;
import com.microservicios.feignclients.BikeFeignClient;
import com.microservicios.feignclients.CarFeignClient;
import com.microservicios.model.Bike;
import com.microservicios.model.Car;
import com.microservicios.repository.UserRepository;



@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CarFeignClient carFeignClient;
	@Autowired
	private BikeFeignClient bikeFeignClient;
	
	public List<User> getAll(){
		return this.userRepository.findAll();
	}
	
	public User getUserById(int id) {
		return this.userRepository.findById(id).orElse(null);
	}
	
	public User save(User user) {
		User userNew=this.userRepository.save(user);
		return userNew;
	}
	
	public List getCars(int userId){
		Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer "+jwt.getTokenValue());
		ResponseEntity<List> cars=restTemplate.exchange("http://car-service/cars/users/"+userId,HttpMethod.GET,new HttpEntity<>(httpHeaders),List.class);
		return cars.getBody();
	}
	
	public List getBikes(int userId){
		Jwt jwt=(Jwt)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders httpHeaders=new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer "+jwt.getTokenValue());
		
		ResponseEntity<List> bikes=restTemplate.exchange("http://bike-service/bikes/users/"+userId,HttpMethod.GET,new HttpEntity<>(httpHeaders), List.class);
		return bikes.getBody();
	}
	
	public Car saveCar(int id, Car car) {
		car.setUserId(id);
		Car carNew=this.carFeignClient.save(car);
		return carNew;
	}
	
	public Bike saveBike(int id, Bike bike) {
		bike.setUserId(id);
		Bike bikeNew=this.bikeFeignClient.save(bike);
		return bikeNew;
	}
	
	public Map<String, Object> getUserAndVehicles(int id){
		Map<String,Object>result=new HashMap();
		User user=userRepository.findById(id).orElse(null);
		if (user==null) {
			result.put("mensaje", "No existe el usuario");
			return result;
		}
		result.put("user", user);
		
		try {
			List<Car>cars=this.carFeignClient.getByUserId(id);
			if (cars==null || cars.isEmpty()) {
				result.put("cars", new Object[0]);
			}else {
				result.put("cars", cars);
			}
		}catch (NotContextException e) {
			result.put("cars", "no existe cars");
		}
		
		try {
			List<Bike>bikes=this.bikeFeignClient.getByUserId(id);
			if (bikes==null || bikes.isEmpty()) {
				result.put("bikes", new Object[0]);
			}else {
				result.put("bikes", bikes);
			}
			
		}catch (NotContextException e) {
			result.put("bikes", "no existe bikes");
		}
		return result;
	}
}
