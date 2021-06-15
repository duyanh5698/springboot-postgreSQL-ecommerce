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

import lombok.Data;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id", insertable = false, updatable = false)
	private Order order;
	
	@Column(name = "order_id")
	private Long orderId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id", insertable = false, updatable = false)
	private Product product;
	
	@Column(name = "product_id")
	private Long productId;
	
	@NotBlank
	private int quantity;
	
	public String getProductName() {
		return this.product != null ? this.product.getName() : "";
	}
	
	public int getProductPrice() {
		return this.product != null ? this.product.getPrice() : null;
	}
		
}
