package com.loan.service.DealFacilityService.User;

import java.net.URI;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


import jakarta.validation.Valid;



@RestController
public class UserController {
	
	@Autowired
	private UserDaoService lUserService;
			
	@GetMapping("/users")
	public List<User> fetchUsers(){
		return lUserService.findAll();
	}
		
	@GetMapping("/users/{id}")
	public EntityModel<User> fetchUser(@PathVariable int id){
		User lUsr = lUserService.findOne(id);
		
		if (lUsr == null) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		EntityModel<User> lEntUsr = EntityModel.of(lUsr);
		
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).fetchUsers());
		lEntUsr.add(link.withRel("all-users"));
		
		return lEntUsr;
	}
		
	@GetMapping("/users-filtered/{id}")
	public MappingJacksonValue fetchUserFiltered(@PathVariable int id){
		User lUsr = lUserService.findOne(id);
				
		if (lUsr == null) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("name");
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserFilter", filter );
		
		MappingJacksonValue mapJackVal = new MappingJacksonValue(lUsr);
		mapJackVal.setFilters(filters);
		/*
		EntityModel<MappingJacksonValue> lEntUsr = EntityModel.of(mapJackVal);
		
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).fetchUsers());
		lEntUsr.add(link.withRel("all-users"));
		*/
		return mapJackVal;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteById(@PathVariable int id){
		lUserService.deleteByID(id);	
	}
			
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user){
		
		User savedUser = lUserService.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
		
}
