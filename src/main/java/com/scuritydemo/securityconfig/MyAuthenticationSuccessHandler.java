package com.scuritydemo.securityconfig;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority->grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
		System.err.println(isAdmin);
		boolean isUser = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority->grantedAuthority.getAuthority().equals("ROLE_USER"));
		System.err.println(isUser);
		boolean isCustomer = authentication.getAuthorities().stream()
				.anyMatch(grantedAuthority->grantedAuthority.getAuthority().equals("ROLE_CUSTOMER"));
		System.err.println(isCustomer);
		
		if(isAdmin) {
			setDefaultTargetUrl("/admin/home");
			//response.sendRedirect("/admin/home");			
		}else if(isUser) {
			setDefaultTargetUrl("/user/home");
			//response.sendRedirect("/user/home");
		}else if(isCustomer) {
			setDefaultTargetUrl("/customer/home");
			//response.sendRedirect("/customer/home");
		}

		
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
