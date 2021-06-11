package com.ecommerce.model.dto;

import java.util.Set;

import com.ecommerce.model.Role;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
	private String tokentype = "Bearer";
	private Long id;
	private String username;
	private String email;
	private int age;
	private Set<Role> roles;

	public JwtResponse(String token, Long id, String username, String email, int age, Set<Role> roles) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.age = age;
		this.roles = roles;
	}
	
	public JwtResponse(String token, Long id, String username, String email, int age) {
		super();
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.age = age;
	}
	
}
