package com.scuritydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scuritydemo.dto.UserDto;
import com.scuritydemo.model.MyUser;
import com.scuritydemo.service.MyUserService;

import jakarta.validation.Valid;

@Controller
public class HomeController {
	
	@Autowired
	private MyUserService userService;
	
	@Autowired
	private PasswordEncoder pwdEncoder;

	@GetMapping("/")
	public String getHomePage() {
		return "homepage";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		
		boolean thereAreAnyUser = userService.thereAreAnyUser();
		
		if(thereAreAnyUser) {
			model.addAttribute("userDto", new UserDto());
			return "login-page";
		}
			
			String roles = "ADMIN,USER,CUSTOMER";
			
			MyUser defaultUser = MyUser.builder()
					.username("admin")
					.password(pwdEncoder.encode("1234"))
					.openpassword("1234")
					.roles(roles)
					.accountNonExpired(true)
					.accountNonLocked(true)
					.credentialsNonExpired(true)
					.enabled(true)
					.build();
			userService.saveMyUser(defaultUser);
			
			model.addAttribute("userDto", new UserDto());
			//return "login-page";
			return "login-page";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result ) {
		if(result.hasErrors()) {
			return "login-page";
		}
		return "redirect:/login?login";
		
	}
	
	
}
