package com.example.rest.processors.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.rest.dao.CarsDAO;
import com.example.rest.domain.Car;
import com.example.rest.domain.Cars;
import com.example.rest.processors.impl.redis.RedisCarsRepository;

@Service("service.carsRedisProcessor")
public class RedisCarsProcessor extends AbstractCarsProcessor {
	
	@Autowired
	private RedisCarsRepository redisCarsRepo;
	
	public RedisCarsProcessor(@Qualifier("rest.carsDAO") CarsDAO carsDAO) {
		super(carsDAO);
	}
	
	@Override
	public ResponseEntity<Cars> processGetCars() {
		final List<Car> carsList;
		try {
			synchronized (redisCarsRepo) {
				if (redisCarsRepo.count() > 0) {
					carsList = new ArrayList<>();
					redisCarsRepo.findAll().forEach(carsList::add);
				} else {
					carsList = carsDAO.getCars();
					if (!CollectionUtils.isEmpty(carsList)) {
						redisCarsRepo.save(carsList);
					}
				}
			}
			
			return ResponseEntity.ok().body(new Cars(carsList));
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@Override
	public ResponseEntity<Car> processGetCar(final int id) {
		try {
			Car car;
			synchronized (redisCarsRepo) {
				car = redisCarsRepo.findOne(id);
				if (car == null) {
					car = carsDAO.getCar(id);
					if (car != null) {
						redisCarsRepo.save(car);
					}
				}
			}
			
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
				synchronized (redisCarsRepo) {
					redisCarsRepo.save(newCar);
				}
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
				synchronized (redisCarsRepo) {
					redisCarsRepo.save(car);
				}
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
				synchronized (redisCarsRepo) {
					redisCarsRepo.delete(id);
				}
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception ex) {
			processException(ex);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
