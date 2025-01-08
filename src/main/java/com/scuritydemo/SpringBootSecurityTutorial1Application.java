package com.scuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.scuritydemo"})
public class SpringBootSecurityTutorial1Application {

	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityTutorial1Application.class, args);
	}

}
