package com.example.rest.processors.impl.redis;

import org.springframework.data.repository.CrudRepository;

import com.example.rest.domain.Car;

public interface RedisCarsRepository extends CrudRepository<Car, Integer> {
	
}
