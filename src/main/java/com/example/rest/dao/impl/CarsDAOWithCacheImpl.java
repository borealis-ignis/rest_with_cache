package com.example.rest.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.rest.dao.CarsDAO;
import com.example.rest.dao.JdbcExt;
import com.example.rest.dao.impl.helpers.CarsRowMapper;
import com.example.rest.domain.Car;

@Repository("rest.withCache.carsDAO")
public class CarsDAOWithCacheImpl extends JdbcExt implements CarsDAO {
	
	private static final String SELECT_CARS = "select id, name from cars";
	
	private static final String SELECT_CAR = "select id, name from cars where id=:id";
	
	private static final String CREATE_CAR = "insert into cars (name) values (:name)";
	
	private static final String UPDATE_CAR = "update cars set name=:name where id=:id";
	
	private static final String DELETE_CAR = "delete from cars where id=:id";
	
	@Override
	@Cacheable(value="carsCacheList", sync=true, cacheManager="cacheManager")
	public List<Car> getCars() {
		System.out.println("get Cars from DB");
		return getJdbcTemplate().query(SELECT_CARS, new CarsRowMapper());
	}
	
	@Override
	@CacheEvict(value="carsCacheList", allEntries=true, cacheManager="cacheManager")
	public void cleanCarsCache() {}
	
	@Override
	@Cacheable(value="carsCache", key="#id", cacheManager="cacheManager")
	public Car getCar(final Integer id) {
		System.out.println("get Car from DB by id " + id);
		final Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		try {
			return getNamedParamJdbcTemplate().queryForObject(SELECT_CAR, params, new CarsRowMapper());
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	@Override
	@CachePut(value="carsCache", key="#result.getId()", unless="#result == null", cacheManager="cacheManager")
	public Car createCar(final Car car) {
		final MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("name", car.getName());
		
		final KeyHolder keyHolder = new GeneratedKeyHolder();
		if (getNamedParamJdbcTemplate().update(CREATE_CAR, params, keyHolder) > 0) {
			car.setId(keyHolder.getKey().intValue());
			System.out.println("created Car with id " + car.getId());
			return car;
		}
		System.out.println("Not created Car");
		return null;
	}
	
	@Override
	@Caching(
		//evict = { @CacheEvict(value="carsCacheList", allEntries=true) },
		put = { @CachePut(value="carsCache", key="#car.getId()", unless="#result == null", cacheManager="cacheManager") }
	)
	public Car updateCar(final Car car) {
		final Map<String, Object> params = new HashMap<>();
		params.put("id", car.getId());
		params.put("name", car.getName());
		
		if (getNamedParamJdbcTemplate().update(UPDATE_CAR, params) > 0) {
			System.out.println("updated Car with id " + car.getId());
			return car;
		}
		System.out.println("Not updated Car");
		return null;
	}
	
	@Override
	@Caching(
		evict = {
			//@CacheEvict(value="carsCacheList", allEntries=true),
			@CacheEvict(value="carsCache", key="#id", condition="#result == true", cacheManager="cacheManager")
		}
	)
	public boolean deleteCar(final Integer id) {
		final Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		
		if (getNamedParamJdbcTemplate().update(DELETE_CAR, params) > 0) {
			System.out.println("deleted Car with id " + id);
			return true;
		}
		System.out.println("Not deleted Car");
		return false;
	}
	
}
