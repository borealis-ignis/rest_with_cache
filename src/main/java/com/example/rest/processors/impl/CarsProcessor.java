package com.example.rest.processors.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.example.rest.dao.CarsDAO;

@Service("service.carsProcessor")
public class CarsProcessor extends AbstractCarsProcessor {
	
	public CarsProcessor(@Qualifier("rest.carsDAO") CarsDAO carsDAO) {
		super(carsDAO);
	}
	
}
