package com.example.rest.cache.redis.aop;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.rest.cache.redis.RedisCarsRepository;
import com.example.rest.cache.redis.aop.annotations.DeleteCarRedisCache;
import com.example.rest.cache.redis.aop.annotations.ReadAllCarsRedisCache;
import com.example.rest.cache.redis.aop.annotations.ReadCarRedisCache;
import com.example.rest.cache.redis.aop.annotations.UpdateCarRedisCache;
import com.example.rest.domain.Car;

@Aspect
@Component
public class RedisCarsAspect {
	
	@Autowired
	private RedisCarsRepository redisCarsRepo;
	
	@SuppressWarnings("unchecked")
	@Around("@annotation(readRedisCache)")
	public List<Car> readCars(final ProceedingJoinPoint joinPoint, final ReadAllCarsRedisCache readRedisCache) throws Throwable {
		final List<Car> carsList;
		synchronized (redisCarsRepo) {
			if (redisCarsRepo.count() > 1) {
				carsList = new ArrayList<>();
				redisCarsRepo.findAll().forEach(carsList::add);
			} else {
				carsList = (List<Car>) joinPoint.proceed();
				if (!CollectionUtils.isEmpty(carsList)) {
					redisCarsRepo.save(carsList);
				}
			}
		}
		return carsList;
	}
	
	@Around("@annotation(readRedisCache)")
	public Car readCar(final ProceedingJoinPoint joinPoint, final ReadCarRedisCache readRedisCache) throws Throwable {
		final Object[] args = joinPoint.getArgs();
		final Integer id = (Integer)args[0];
		
		Car car;
		synchronized (redisCarsRepo) {
			car = redisCarsRepo.findOne(id);
			if (car == null) {
				car = (Car) joinPoint.proceed(args);
				if (car != null) {
					redisCarsRepo.save(car);
				}
			}
		}
		
		return car;
	}
	
	@Around("@annotation(updateRedisCache)")
	public Car updateCar(final ProceedingJoinPoint joinPoint, final UpdateCarRedisCache updateRedisCache) throws Throwable {
		final Car car = (Car) joinPoint.proceed();
		if (car != null) {
			synchronized (redisCarsRepo) {
				redisCarsRepo.save(car);
			}
		}
		
		return car;
	}
	
	@Around("@annotation(deleteRedisCache)")
	public boolean deleteCar(final ProceedingJoinPoint joinPoint, final DeleteCarRedisCache deleteRedisCache) throws Throwable {
		final Object[] args = joinPoint.getArgs();
		
		boolean result = (boolean) joinPoint.proceed(args);
		if (result) {
			final Integer id = (Integer)args[0];
			
			synchronized (redisCarsRepo) {
				redisCarsRepo.delete(id);
			}
		}
		
		return result;
	}
}
