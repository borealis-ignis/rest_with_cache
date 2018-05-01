package com.example.rest.processors;

import org.springframework.http.ResponseEntity;

import com.example.rest.domain.Car;
import com.example.rest.domain.Cars;

public interface ICarsProcessor {
	
	ResponseEntity<Cars> processGetCars();
	
	ResponseEntity<Car> processGetCar(int id);
	
	ResponseEntity<Car> processCreateCar(Car car);
	
	ResponseEntity<?> processUpdateCar(Car car);
	
	ResponseEntity<?> processDeleteCar(int id);
	
}
