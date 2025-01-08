package com.scuritydemo.securityconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scuritydemo.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired private MyUserDetailsService userDetailsService;
	@Autowired private MyAuthenticationSuccessHandler authSuccessHandler;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}
	
	@Bean
	public PasswordEncoder pwdEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider outhProvider = new DaoAuthenticationProvider();
		outhProvider.setUserDetailsService(userDetailsService);
		outhProvider.setPasswordEncoder(pwdEncoder());
		return outhProvider;
	}
	
	@Bean
	public SecurityFilterChain customSecurityFilterChain(HttpSecurity sec) throws Exception {
		return sec
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(req->{
			req.requestMatchers("/", "/home").permitAll();
			req.requestMatchers("/register/**").permitAll();
			req.requestMatchers("/admin/**").hasRole("ADMIN");
			req.requestMatchers("/user/**").hasRole("USER");
			req.requestMatchers("/customer/**").hasRole("CUSTOMER");
			req.anyRequest().authenticated();
			
		})
		
		.formLogin(login->{
			login
			.loginPage("/login")
			.loginProcessingUrl("/login")
			//.defaultSuccessUrl("/admin/home")
			.successHandler(authSuccessHandler)
			
			.permitAll();
					
			
		})
		//.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
		
		.logout(logout->{
			logout.logoutSuccessUrl("/");
		})
		
		.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails normalUser = User.builder()
//					
//				.username("user")
//				.password(pwdEncoder().encode("1234"))
//				.roles("USER")
//				.build();
//		
//		UserDetails adminUser = User.builder()
//				
//				.username("admin")
//				.password(pwdEncoder().encode("1234"))
//				.roles("ADMIN","USER","CUSTOMER")
//				.build();
//		
//		UserDetails customerUser = User.builder()
//				
//				.username("muammer")
//				.password(pwdEncoder().encode("1234"))
//				.roles("CUSTOMER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(normalUser, adminUser, customerUser);
//	}
	
	

}
