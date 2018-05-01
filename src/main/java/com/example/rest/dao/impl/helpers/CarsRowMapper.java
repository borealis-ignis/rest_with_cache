package com.example.rest.dao.impl.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.rest.domain.Car;

public class CarsRowMapper implements RowMapper<Car> {
	
	@Override
	public Car mapRow(ResultSet rs, int i) throws SQLException {
		final Integer id = rs.getInt("id");
		final String name = rs.getString("name");
		return new Car(id, name);
	}
	
}