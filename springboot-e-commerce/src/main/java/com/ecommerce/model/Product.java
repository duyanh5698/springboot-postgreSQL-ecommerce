package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private int quantity;
	
	@NotBlank
	private int price;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	@Column(name = "user_id")
	private Long userId;


	public String getCreateBy() {
		return this.user != null ? this.user.getEmail() : "";
	}

	public Product(@NotBlank String name, @NotBlank String decription, @NotBlank int quantity, @NotBlank int price, Long userId) {
		super();
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.userId = userId;
	}
	
	public void set(Product editProduct) {
			this.name = editProduct.getName();
			this.description = editProduct.getDescription();
			this.quantity = editProduct.getQuantity();
			this.price = editProduct.getPrice();
			this.userId = editProduct.getUserId();
	}
	
}
