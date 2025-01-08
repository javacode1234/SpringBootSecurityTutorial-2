package com.scuritydemo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class UserDto {
	
	private Integer id;

	@NotEmpty(message = "Kullanıcı Adı boş olamaz !!")	
	private String username;
	
	@NotEmpty(message = "Şifre boş olamaz !!")
	private String password;
	
	//@NotEmpty(message = "Şifre boş olamaz !!")
	@NotNull(message = "En az bir Role seçmelisin !!")
	private String roles;
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
}
