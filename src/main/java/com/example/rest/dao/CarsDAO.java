package com.example.rest.dao;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.rest.domain.Car;

public interface CarsDAO {
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=true, propagation=Propagation.REQUIRED)
	List<Car> getCars();
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=false, propagation=Propagation.REQUIRED)
	void cleanCarsCache();
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=true, propagation=Propagation.REQUIRED)
	Car getCar(Integer id);
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=false, propagation=Propagation.REQUIRED)
	Car createCar(Car car);
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=false, propagation=Propagation.REQUIRED)
	Car updateCar(Car car);
	
	@Transactional(noRollbackFor=Throwable.class, readOnly=false, propagation=Propagation.REQUIRED)
	boolean deleteCar(Integer id);
	
}
