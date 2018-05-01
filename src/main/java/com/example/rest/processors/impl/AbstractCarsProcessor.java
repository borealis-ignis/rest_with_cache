package com.example.rest.processors.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.rest.dao.CarsDAO;
import com.example.rest.domain.Car;
import com.example.rest.domain.Cars;
import com.example.rest.processors.ICarsProcessor;

public abstract class AbstractCarsProcessor implements ICarsProcessor {
	
	protected CarsDAO carsDAO;
	
	public AbstractCarsProcessor(final CarsDAO carsDAO) {
		this.carsDAO = carsDAO;
	}
	
	@Override
	public ResponseEntity<Cars> processGetCars() {
		try {
			return ResponseEntity.ok().body(new Cars(carsDAO.getCars()));
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<Car> processGetCar(final int id) {
		try {
			final Car car = carsDAO.getCar(id);
			
			if (car == null) {
				return ResponseEntity.badRequest().build();
			}
			return ResponseEntity.ok().body(car);
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<Car> processCreateCar(final Car car) {
		try {
			final Car newCar = carsDAO.createCar(car);
			if (newCar != null) {
				carsDAO.cleanCarsCache();
				return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<?> processUpdateCar(final Car car) {
		try {
			if (carsDAO.updateCar(car) != null) {
				carsDAO.cleanCarsCache();
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<?> processDeleteCar(final int id) {
		try {
			if (carsDAO.deleteCar(id)) {
				carsDAO.cleanCarsCache();
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	protected void processException(final Exception ex) {
		ex.printStackTrace();
	}
	
}
