package com.example.rest.processors.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.rest.dao.CarsDAO;

@Service("service.carsEhCacheProcessor")
public class CarsEhCacheProcessor extends AbstractCarsProcessor {
	
	public CarsEhCacheProcessor(@Qualifier("rest.withEhCache.carsDAO") CarsDAO carsDAO) {
		super(carsDAO);
	}
	
}
