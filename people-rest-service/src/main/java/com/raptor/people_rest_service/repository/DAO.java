package com.raptor.people_rest_service.repository;

import java.util.List;

import com.raptor.people_rest_service.entity.Person;

public interface DAO <T> {

	int save(T t);
	
	int update(T t);
	
	Person findById(int id);
	
	int deleteById(int id);
	
	List<T> findAll();
	
	int deleteAll();
	
}
