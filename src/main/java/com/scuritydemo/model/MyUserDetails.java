package com.scuritydemo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private MyUser myUser;

	public MyUserDetails(MyUser myUser) {
		this.myUser=myUser;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		String[] roles = myUser.getRoles().split(",");
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for(String role : roles) {
			authorities.add(new SimpleGrantedAuthority( "ROLE_" + role ));
		}
		
		return authorities;
	}

	public String getPassword() {
		return myUser.getPassword();
	}

	public String getUsername() {
		return myUser.getUsername();
	}
	
	public boolean isAccountNonExpired() {
		return myUser.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return myUser.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return myUser.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return myUser.isEnabled();
	}


}
