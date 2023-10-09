package com.loan.service.DealFacilityService.User;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonFilter;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


//@JsonIgnoreProperties("name")
//@JsonFilter("UserFilter")
@Entity(name="user_details")
public class User {
	@Id
	@GeneratedValue(generator="seq")
    @GenericGenerator(name = "seq")
	private Integer id;
	@Size(min=2, message="{Name} must have at least 2 characters.")
	@JsonProperty("User Name")
	private String name;
	@Past (message="{BirthDate} must be in the past.")
	@JsonProperty("Birth Date")
	//@JsonIgnore
	private LocalDate birthDate;
	
	@OneToMany(mappedBy = "lUsr")
	@JsonIgnore
	private List<Post> lstPosts;
	
	public User() {
		id=0;
		name = "";
		birthDate = LocalDate.now().minusYears(200);
	}
	
	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}


	public List<Post> getLstPosts() {
		return lstPosts;
	}

	public void setLstPosts(List<Post> lstPosts) {
		this.lstPosts = lstPosts;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthDate=" + birthDate + "]";
	}
	
}
