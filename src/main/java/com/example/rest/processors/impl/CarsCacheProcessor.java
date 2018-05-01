package com.example.rest.processors.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.rest.dao.CarsDAO;

@Service("service.carsCacheProcessor")
public class CarsCacheProcessor extends AbstractCarsProcessor {
	
	public CarsCacheProcessor(@Qualifier("rest.withCache.carsDAO") CarsDAO carsDAO) {
		super(carsDAO);
	}
	
}
