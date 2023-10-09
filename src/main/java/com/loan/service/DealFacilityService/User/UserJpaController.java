package com.loan.service.DealFacilityService.User;

import java.net.URI;
import java.util.List;

import java.util.Optional;
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
import com.loan.service.DealFacilityService.jpa.PostRepository;
import com.loan.service.DealFacilityService.jpa.UserRepository;

import jakarta.validation.Valid;



@RestController
public class UserJpaController {
	
	
	@Autowired
	private UserRepository lUsrRepo;
	
	@Autowired
	private PostRepository lPostRepo;
	
	
	@GetMapping("/jpa/users")
	public List<User> fetchJpaUsers(){
		return lUsrRepo.findAll();
	}

		
	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> fetchJpaUser(@PathVariable int id){
		Optional<User> lOptUsr = lUsrRepo.findById(id);
		
		if (lOptUsr.isEmpty()) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		User lUsr = lOptUsr.get();
		
		EntityModel<User> lEntUsr = EntityModel.of(lUsr);
		
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).fetchJpaUsers());
		lEntUsr.add(link.withRel("all-users"));
		
		return lEntUsr;
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> fetchJpaPosts(@PathVariable int id){
		Optional<User> lOptUsr = lUsrRepo.findById(id);
		
		if (lOptUsr.isEmpty()) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		User lUsr = lOptUsr.get();
		
		
		return lUsr.getLstPosts();
	}
	
	@GetMapping("/jpa/users-filtered/{id}")
	public MappingJacksonValue fetchJpaUserFiltered(@PathVariable int id){
		Optional<User> lOptUsr = lUsrRepo.findById(id);
		
		if (lOptUsr.isEmpty()) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		User lUsr = lOptUsr.get();
		
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
	
	
	
	@DeleteMapping("/jpa/users/{id}")
	public void deleteJpaById(@PathVariable int id){
		lUsrRepo.deleteById(id);
		
	}
		
	
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> addJpaUser(@Valid @RequestBody User user){
		
		User savedUser = lUsrRepo.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<User> addJpaPostForUsr(@PathVariable int id, @Valid @RequestBody Post post){
		
		Optional<User> lOptUsr = lUsrRepo.findById(id);
		
		if (lOptUsr.isEmpty()) {
			throw new UserNotFoundException("id = " + id + " not found!");
		}
		
		User lUsr = lOptUsr.get();
		
		post.setlUsr(lUsr);
		
		Post lpost = lPostRepo.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(lpost.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
}
