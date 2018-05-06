package com.example.rest.processors.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.rest.dao.CarsDAO;

@Service("service.carsRedisProcessor")
public class RedisCarsProcessor extends AbstractCarsProcessor {
	
	public RedisCarsProcessor(@Qualifier("rest.withRedisCache.carsDAO") CarsDAO carsDAO) {
		super(carsDAO);
	}
	
}
