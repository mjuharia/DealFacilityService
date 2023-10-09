package com.loan.service.DealFacilityService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// All Requests should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		//Authenticate request with defaults
		http.httpBasic(Customizer.withDefaults());
		
		// Disable CSRF for Post, Put, Delete requests
		http.csrf().disable();
		
		
		return http.build();
	}
}
