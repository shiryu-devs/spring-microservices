package com.microservicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservicios.entity.Bike;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {

	public List<Bike> findByUserId(int $userId);
}
