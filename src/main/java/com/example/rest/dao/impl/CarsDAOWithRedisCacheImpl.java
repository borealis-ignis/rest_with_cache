package com.example.rest.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.rest.cache.redis.aop.annotations.DeleteCarRedisCache;
import com.example.rest.cache.redis.aop.annotations.ReadAllCarsRedisCache;
import com.example.rest.cache.redis.aop.annotations.ReadCarRedisCache;
import com.example.rest.cache.redis.aop.annotations.UpdateCarRedisCache;
import com.example.rest.domain.Car;

@Repository("rest.withRedisCache.carsDAO")
public class CarsDAOWithRedisCacheImpl extends CarsDAOImpl {
	
	@Override
	@ReadAllCarsRedisCache
	public List<Car> getCars() {
		return super.getCars();
	}
	
	@Override
	@ReadCarRedisCache
	public Car getCar(Integer id) {
		return super.getCar(id);
	}
	
	@Override
	@UpdateCarRedisCache
	public Car createCar(Car car) {
		return super.createCar(car);
	}
	
	@Override
	@UpdateCarRedisCache
	public Car updateCar(Car car) {
		return super.updateCar(car);
	}
	
	@Override
	@DeleteCarRedisCache
	public boolean deleteCar(Integer id) {
		return super.deleteCar(id);
	}
}
