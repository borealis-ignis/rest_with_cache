package com.example.rest.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@RedisHash("cars")
public class Car implements Serializable {
	
	private static final long serialVersionUID = -1347354355706283126L;

	@XmlElement
	@Id
	private Integer id;
	
	@XmlElement
	private String name;
	
	public Car() {}
	
	public Car(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
}
