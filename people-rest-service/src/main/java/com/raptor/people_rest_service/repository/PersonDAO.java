package com.raptor.people_rest_service.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.raptor.people_rest_service.entity.Person;

@Repository
public class PersonDAO implements DAO <Person> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int save(Person person) {
		return jdbcTemplate.update("INSERT INTO person (first_name, last_name, email, age) " +
					"VALUES (?, ?, ?, ?)", new Object[] { person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge() });
	}

	@Override
	public int update(Person person) {
		return jdbcTemplate.update("UPDATE person SET first_name = ?, last_name = ?, " +
					"email = ?, age = ? WHERE id = ?",
					new Object[] {person.getFirstName(), person.getLastName(),
							person.getEmail(), person.getAge(), person.getId()});
	}

	@Override
	public Person findById(int id) {
		try {
			Person person = jdbcTemplate.queryForObject("SELECT id, first_name, last_name, email, age FROM person WHERE id = ?",
					BeanPropertyRowMapper.newInstance(Person.class), id);
			
			return person;
		} catch (IncorrectResultSizeDataAccessException exc) {
			return null;
		}
	}

	@Override
	public int deleteById(int id) {
		return jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
	}

	@Override
	public List<Person> findAll() {
		
		return jdbcTemplate.query("SELECT id, first_name, last_name, email, age FROM person", BeanPropertyRowMapper.newInstance(Person.class));
	}

	@Override
	public int deleteAll() {
		return jdbcTemplate.update("DELETE from person");
	}

}
