package com.ecommerce.model;


import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	@Column(name = "user_id")
	private long userId;
	
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean approved;
	
//	@Transient
//	private List<OrderDetails> orderDetails = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = { CascadeType.ALL }, orphanRemoval = true)
//	@Where(clause = "active = 'TRUE'")
	private List<OrderDetails> orderDetails = new ArrayList<>();
	
	public String getOrderBy() {
		return this.user != null ? this.user.getEmail() : "";
	}
	
}
