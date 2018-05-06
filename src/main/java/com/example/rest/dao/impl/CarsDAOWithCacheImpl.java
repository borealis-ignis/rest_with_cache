package com.example.rest.dao.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import com.example.rest.domain.Car;

@Repository("rest.withCache.carsDAO")
public class CarsDAOWithCacheImpl extends CarsDAOImpl {
	
	@Override
	@Cacheable(value="carsCacheList", sync=true, cacheManager="cacheManager")
	public List<Car> getCars() {
		return super.getCars();
	}
	
	@Override
	@CacheEvict(value="carsCacheList", allEntries=true, cacheManager="cacheManager")
	public void cleanCarsCache() {}
	
	@Override
	@Cacheable(value="carsCache", key="#id", cacheManager="cacheManager")
	public Car getCar(final Integer id) {
		return super.getCar(id);
	}
	
	@Override
	@CachePut(value="carsCache", key="#result.getId()", unless="#result == null", cacheManager="cacheManager")
	public Car createCar(final Car car) {
		return super.createCar(car);
	}
	
	@Override
	@Caching(
		//evict = { @CacheEvict(value="carsCacheList", allEntries=true) },
		put = { @CachePut(value="carsCache", key="#car.getId()", unless="#result == null", cacheManager="cacheManager") }
	)
	public Car updateCar(final Car car) {
		return super.updateCar(car);
	}
	
	@Override
	@Caching(
		evict = {
			//@CacheEvict(value="carsCacheList", allEntries=true),
			@CacheEvict(value="carsCache", key="#id", condition="#result == true", cacheManager="cacheManager")
		}
	)
	public boolean deleteCar(final Integer id) {
		return super.deleteCar(id);
	}
	
}
