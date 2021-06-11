package com.ecommerce.model;

public enum ERole {
	ROLE_USER("Customer"),
	ROLE_ADMIN("Admin");
	
	ERole(String name) {
		this.name = name;
	}

	public String name;
	
	
}
