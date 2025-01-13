package com.scuritydemo.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scuritydemo.dto.UserDto;
import com.scuritydemo.model.MyUser;
import com.scuritydemo.service.MyUserService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MyUserService myUserService;
	
	@Autowired
	private PasswordEncoder pwdEncoder;

	
	@GetMapping("/home")
	public String getAdminHomePage(Model model, Principal principal) {
		MyUser myUser = myUserService.getMyUserByUsername(principal.getName());
		model.addAttribute("currentUser", myUser);
		return "adminhomepage";
	}
	
	@GetMapping("/users")
	public String getAddUserForm( Model model, Principal principal) {
		MyUser myUser = myUserService.getMyUserByUsername(principal.getName());
		model.addAttribute("currentUser", myUser);
		model.addAttribute("userDto", new UserDto());
		//model.addAttribute("modalUserDto", new UserDto()); modal kayıt iptal.
		List<MyUser> dbUsers = myUserService.findAllUsers();
		model.addAttribute("dbUsers", dbUsers);
		List<String> roles = new ArrayList<>(Arrays.asList("ADMIN","USER","CUSTOMER"));
		model.addAttribute("roles", roles);
		return "add-user";
	}
	
	@PostMapping("/users")
	public String saveUserDto(@Valid @ModelAttribute("userDto") UserDto userDto, 
								BindingResult result, Model model,
								RedirectAttributes redirectAttr,
								@RequestParam("stringResim") MultipartFile file
							) throws IOException {		
		
		boolean userExist = myUserService.userExistForNewUser(userDto.getUsername());
		
		if(userExist) {
			
			model.addAttribute("userDto", userDto);
			List<MyUser> dbUsers = myUserService.findAllUsers();
			model.addAttribute("dbUsers", dbUsers);
			List<String> roles = new ArrayList<>(Arrays.asList("ADMIN","USER","CUSTOMER"));
			model.addAttribute("roles", roles);
			
			model.addAttribute("existUsername", userDto.getUsername());
			model.addAttribute("userExist", userExist);
			
			return "add-user";
		}
									
		if(result.hasErrors()) {
			
			model.addAttribute("userDto", userDto );
			List<MyUser> dbUsers = myUserService.findAllUsers();
			model.addAttribute("dbUsers", dbUsers);
			List<String> roles = new ArrayList<>(Arrays.asList("ADMIN","USER","CUSTOMER"));
			model.addAttribute("roles", roles);
			
			return "add-user";
		}
		
		if(file.getBytes()!=null && !file.getOriginalFilename().isEmpty()) {
			MyUser user = MyUser.builder()
					.id(userDto.getId())
					.image(Base64.getEncoder().encodeToString(file.getBytes()))
					.firstname(userDto.getFirstname())
					.lastname(userDto.getLastname())
					.username(userDto.getUsername())
					.password(pwdEncoder.encode(userDto.getPassword()))
					.openpassword(userDto.getPassword())
					.roles(userDto.getRoles())
					.accountNonExpired(userDto.isAccountNonExpired())
					.accountNonLocked(userDto.isAccountNonLocked())
					.credentialsNonExpired(userDto.isCredentialsNonExpired())
					.enabled(userDto.isEnabled())
					.build();
			
			myUserService.saveMyUser(user);
		}else {
		
			MyUser user = MyUser.builder()
					.username(userDto.getUsername())
					.password(pwdEncoder.encode(userDto.getPassword()))
					.openpassword(userDto.getPassword())
					.roles(userDto.getRoles())
					.accountNonExpired(userDto.isAccountNonExpired())
					.accountNonLocked(userDto.isAccountNonLocked())
					.credentialsNonExpired(userDto.isCredentialsNonExpired())
					.enabled(userDto.isEnabled())
					.build();
			
			myUserService.saveMyUser(user);
		}
		return "redirect:/admin/users";
	}
	
	@PostMapping("/update/user")
	public String updateUserDtoForModal(@Valid @ModelAttribute("userDto") UserDto userDto, 
											BindingResult result, Model model,
											RedirectAttributes redirectAttr, 
											@RequestParam("stringResim") MultipartFile file
										) throws IOException {
		
		
		boolean userExist = myUserService.userExistForUpdate(userDto.getUsername(), userDto.getId() );
		MyUser updateUser = myUserService.getMyUserById(userDto.getId()).get();
		
		if(userExist) {
			model.addAttribute("userDto", userDto);
			List<MyUser> dbUsers = myUserService.findAllUsers();
			model.addAttribute("dbUsers", dbUsers);
			List<String> roles = new ArrayList<>(Arrays.asList("ADMIN","USER","CUSTOMER"));
			model.addAttribute("roles", roles);
			
			redirectAttr.addFlashAttribute("existUsername", userDto.getUsername());
			redirectAttr.addFlashAttribute("userExist", true);
			return "redirect:/admin/users";
		}
		
		if(result.hasErrors()) {
			model.addAttribute("userDto", userDto);
			List<MyUser> dbUsers = myUserService.findAllUsers();
			model.addAttribute("dbUsers", dbUsers);
			List<String> roles = new ArrayList<>(Arrays.asList("ADMIN","USER","CUSTOMER"));
			model.addAttribute("roles", roles);
			
			return "add-user";
		}
		
		if(file.getBytes()!=null && !file.getOriginalFilename().isEmpty()) {
			MyUser user = MyUser.builder()
					.id(userDto.getId())
					.image(Base64.getEncoder().encodeToString(file.getBytes()))
					.firstname(userDto.getFirstname())
					.lastname(userDto.getLastname())
					.username(userDto.getUsername())
					.password(pwdEncoder.encode(userDto.getPassword()))
					.openpassword(userDto.getPassword())
					.roles(userDto.getRoles())
					.accountNonExpired(userDto.isAccountNonExpired())
					.accountNonLocked(userDto.isAccountNonLocked())
					.credentialsNonExpired(userDto.isCredentialsNonExpired())
					.enabled(userDto.isEnabled())
					.build();
			
			myUserService.saveMyUser(user);
		}else {
			MyUser user = MyUser.builder()
					.id(userDto.getId())
					.firstname(userDto.getFirstname())
					.lastname(userDto.getLastname())
					.image(updateUser.getImage())
					.username(userDto.getUsername())
					.password(pwdEncoder.encode(userDto.getPassword()))
					.openpassword(userDto.getPassword())
					.roles(userDto.getRoles())
					.accountNonExpired(userDto.isAccountNonExpired())
					.accountNonLocked(userDto.isAccountNonLocked())
					.credentialsNonExpired(userDto.isCredentialsNonExpired())
					.enabled(userDto.isEnabled())
					.build();
			
			myUserService.saveMyUser(user);
		}
		
		return "redirect:/admin/users";
	}
	
	@GetMapping("/get/user/{id}")
	@ResponseBody
	public MyUser getMyUserById(@PathVariable Integer id) {
		return myUserService.getMyUserById(id).get();
	}
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/delete/user/{id}", method = { RequestMethod.DELETE, RequestMethod.GET} )
	public String deleteUserById(@PathVariable("id") Integer id) {
		myUserService.deleteMyUserById(id);
		return "redirect:/admin/users";
	}
	
	
	
	
	
}
