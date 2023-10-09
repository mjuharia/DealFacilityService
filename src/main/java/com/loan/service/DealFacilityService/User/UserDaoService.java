package com.loan.service.DealFacilityService.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.function.Predicate;


import org.springframework.stereotype.Component;


@Component
public class UserDaoService {
	
	
	private static List<User> userList = new ArrayList<>();
	private static int userCount = 0;
	
	static {
		userList.add(new User(++userCount, "Adam Smith", LocalDate.now().minusYears(30)));
		userList.add(new User(++userCount, "Sandra Splitt", LocalDate.now().minusYears(45)));
		userList.add(new User(++userCount, "Ryan Bulky", LocalDate.now().minusYears(25)));
	}
	
	public List<User> findAll(){
		
		return userList;
	}
	
	public User saveUser(User user) {
	
		user.setId(++userCount);
		userList.add(user);
		return user;
	}
	
	public User findOne(int id) {
		
		Predicate<? super User> predicate = user -> user.getId().equals(id); 
		return userList.stream().filter(predicate).findFirst().orElse(null);
		
	}
	
	public void deleteByID(int id) {
				
		Predicate<? super User> predicate = user -> user.getId().equals(id); 
		userList.removeIf(predicate);
		
	}
}
