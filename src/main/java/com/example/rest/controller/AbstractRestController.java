package com.example.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.rest.domain.Car;
import com.example.rest.domain.Cars;
import com.example.rest.processors.ICarsProcessor;

public abstract class AbstractRestController {
	
	private ICarsProcessor carsProcessor;
	
	protected void setCarsProcessor(final ICarsProcessor carsProcessor) {
		this.carsProcessor = carsProcessor;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, path = "/getcars")
	public ResponseEntity<Cars> getCars() {
		System.out.println("select");
		
		return carsProcessor.processGetCars();
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.GET, path = "/getcar")
	public ResponseEntity<Car> getCar(@RequestParam(value = "id", required = true) final int id) {
		System.out.println("select id: " + id);
		
		return carsProcessor.processGetCar(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/createcar")
	public ResponseEntity<Car> createCar(@RequestBody(required = true) final Car car) {
		System.out.println("create: " + car.getId() + " - " + car.getName());
		
		return carsProcessor.processCreateCar(car);
	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updatecar")
	public ResponseEntity<?> updateCar(@RequestBody(required = true) final Car car) {
		System.out.println("update: " + car.getId() + " - " + car.getName());
		
		return carsProcessor.processUpdateCar(car);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/deletecar")
	public ResponseEntity<?> deleteCar(@RequestParam(value = "id", required = true) final int id) {
		System.out.println("delete: " + id);
		
		return carsProcessor.processDeleteCar(id);
	}
}
