package com.ecommerce.model;

public enum ERole {
	ROLE_USER("Customer"), ROLE_ADMIN("Admin");

	ERole(String name) {
		this.roleName = name;
	}

	public String roleName;

}
