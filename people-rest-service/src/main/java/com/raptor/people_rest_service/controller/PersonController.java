package com.raptor.people_rest_service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raptor.people_rest_service.entity.Person;
import com.raptor.people_rest_service.repository.DAO;

@RestController
@RequestMapping("/api")
public class PersonController {

	@Autowired
	DAO<Person> dao;

	@GetMapping("/persons")
	public ResponseEntity<List<Person>> getAllTutorials() {
		
		try {
		
			List <Person> persons = new ArrayList<Person>();
			
			dao.findAll().forEach(persons::add);
			
			if (persons.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(persons, HttpStatus.OK);
		
		} catch (Exception exc) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@GetMapping("/persons/{id}")
	public ResponseEntity<Person> getPersonById(@PathVariable int id) {
		
		Person person = dao.findById(id);
		
		if (person != null) {
			return new ResponseEntity<>(person, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping("/persons")
	public ResponseEntity<String> createPerson(@RequestBody Person person) {
		try {
			
			dao.save(new Person(person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge()));
			return new ResponseEntity<>("Person was created successfully.", HttpStatus.CREATED);
		} catch (Exception exc) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/persons/{id}")
	public ResponseEntity<String> updatePerson(@PathVariable int id, @RequestBody Person person) {
		
		Person _person = dao.findById(id);
		
		if (_person != null) {
			_person.setId(id);
			_person.setFirstName(person.getFirstName());
			_person.setLastName(person.getLastName());
			_person.setEmail(person.getEmail());
			_person.setAge(person.getAge());
			
			dao.update(_person);
			
			return new ResponseEntity<>("Person was updated successfully.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Cannot find the person with id = " + id, HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/persons/{id}")
	public ResponseEntity<String> deletePerson(@PathVariable int id) {
		
		try {
			int result = dao.deleteById(id);
			
			if (result == 0) {
				return new ResponseEntity<>("Cannot find the person with id = " + id, HttpStatus.OK);
			}
			
			return new ResponseEntity<>("Person was deleted successfully.", HttpStatus.OK);
		} catch (Exception exc) {
			return new ResponseEntity<>("Cannot delete person", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/persons")
	public ResponseEntity<String> deleteAllPersons() {
		try {
			int rowsAffected = dao.deleteAll();
			
			return new ResponseEntity<>("Deleted " + rowsAffected + " person(s) successfully.", HttpStatus.OK);
		} catch (Exception exc) {
			return new ResponseEntity<>("Cannot delete persons", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
