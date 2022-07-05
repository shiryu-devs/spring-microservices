package com.microservicios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicios.entity.Bike;
import com.microservicios.repository.BikeRepository;

@Service
public class BikeService {

	@Autowired
	private BikeRepository bikeRepository;
	
	public List<Bike> getAll(){
		return this.bikeRepository.findAll();
	}
	
	public List<Bike> getBikeByUserId(int userId){
		return this.bikeRepository.findByUserId(userId);
	}
	
	public Bike getBikeById(int id) {
		return this.bikeRepository.findById(id).orElse(null);
	}
	
	public Bike save(Bike bike) {
		Bike bikeNew=this.bikeRepository.save(bike);
		return bikeNew;
	}
}
