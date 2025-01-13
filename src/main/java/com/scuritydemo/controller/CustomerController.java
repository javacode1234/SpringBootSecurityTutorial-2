package com.scuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

		
	@GetMapping("/home")
	public String getUserHomePage() {
		return "customerhomepage";
	}
	
	
}
