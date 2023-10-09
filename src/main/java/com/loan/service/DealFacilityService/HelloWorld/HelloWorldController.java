package com.loan.service.DealFacilityService.HelloWorld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.loan.service.DealFacilityService.HelloWorld.HelloWorldBean;

@RestController
public class HelloWorldController {

	@Autowired
	private MessageSource message;
	
	/*
	public HelloWorldController(MessageSource message) {
		this.message = message;
	}
	*/
	//@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "Hello World!!";
		
	}
	
	@GetMapping("/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		
		return new HelloWorldBean("Hello World!!");
		
	}
	
	@GetMapping("/hello-world-bean-i18")
	public HelloWorldBean helloWorldBean_i18() {
		Locale loc = LocaleContextHolder.getLocale();
		String msg = message.getMessage("good.morning.message", null, "Default Message", loc);
		return new HelloWorldBean(msg);
		
	}
	
	@GetMapping("/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldBean(@PathVariable String name) {
		
		return new HelloWorldBean("Hello World from " + name);
		
	}
}
