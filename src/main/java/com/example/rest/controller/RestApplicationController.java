package com.example.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.processors.ICarsProcessor;

@RestController
@RequestMapping(path = "/cars")
public class RestApplicationController extends AbstractRestController {
	
	@Override
	@Autowired
	@Qualifier("service.carsProcessor")
	protected void setCarsProcessor(final ICarsProcessor carsProcessor) {
		super.setCarsProcessor(carsProcessor);
	}
	
}
