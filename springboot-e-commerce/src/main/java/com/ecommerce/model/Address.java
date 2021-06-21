package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ecommerce.exception.RestApiException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String addrezz;

	@NotNull
	private String city;

	@NotNull
	private String state;

	@NotNull
	private String country;

	@NotNull
	private String zipcode;

	@NotNull
	private String phoneNumber;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "user_id")
	private Long userId;

	public void set(Address editAddress) {
		this.addrezz = editAddress.getAddrezz();
		this.city = editAddress.getCity();
		this.state = editAddress.getState();
		this.country = editAddress.getCountry();
		this.zipcode = editAddress.getZipcode();
		this.userId = editAddress.getUserId();
	}
	
	public void valid() throws RestApiException {
		if(this.addrezz == null) throw new RestApiException("Address cannot be blank!");
		if(this.city == null) throw new RestApiException("City cannot be blank!");
		if(this.state == null) throw new RestApiException("State cannot be blank!");
		if(this.country == null) throw new RestApiException("Country cannot be blank!");
		if(this.zipcode == null) throw new RestApiException("Zipcode cannot be blank!");
		if(this.phoneNumber == null) throw new RestApiException("Phone Number cannot be blank!");
	}
}
