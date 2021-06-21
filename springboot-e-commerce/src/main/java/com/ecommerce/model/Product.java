package com.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.ecommerce.exception.RestApiException;
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
	@Column
	private int quantity;
	
	@Column
	@NotBlank
	private int price;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "user_id", updatable = false)
	private long userId;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = { CascadeType.ALL }, orphanRemoval = true)
	private List<File> files = new ArrayList<>();
	
	public List<String> getImageURL() {
		List<String> uRLs = files.stream().map(i -> i.getUrl()).collect(Collectors.toList());
		return uRLs;
	}

	public String getCreateBy() {
		return this.user != null ? this.user.getEmail() : "";
	}

	public Product(@NotBlank String name, @NotBlank String description, @NotBlank int quantity, @NotBlank int price,
			long userId) {
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

	public void valid() throws RestApiException {
		if(this.name == null) throw new RestApiException("Name cannot be blank!");
		if(this.description == null) throw new RestApiException("Description cannot be blank!");
	}

}
