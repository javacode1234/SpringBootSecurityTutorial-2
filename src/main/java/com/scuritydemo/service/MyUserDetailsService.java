package com.scuritydemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scuritydemo.model.MyUser;
import com.scuritydemo.model.MyUserDetails;
import com.scuritydemo.repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired private MyUserRepository userRepo;
	//@Autowired private PasswordEncoder pwdEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<MyUser> user = userRepo.findByUsername(username);
		
		if(!user.isPresent()) {
			throw new UsernameNotFoundException(username+" username not found !!! ");
		}
		
		return new MyUserDetails(user.get());
		
//		if(user.isPresent()) {
//			var userObj = user.get();
//			return User.builder()
//					.username(userObj.getUsername())
//					.password(userObj.getPassword())
//					.roles(getRoles(userObj))
//					.build();
//		}else {			
//			throw new UsernameNotFoundException(username+" username not found !!! ");			
//		}
		
		
	}

//	private String[] getRoles(MyUser user) {
//		if(user.getRoles()==null) {
//			return new String[]{"ADMIN","USER","CUSTOMER"};
//		}
//		return user.getRoles().split(",");
//	}

}
