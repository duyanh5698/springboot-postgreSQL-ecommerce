package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.Address;
import com.ecommerce.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	AddressService addressService;

	@GetMapping("/list")
	public ResponseEntity<List<Address>> getAllAddresses() {
		return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Address> create(@RequestBody Address address) throws RestApiException {
		return new ResponseEntity<>(addressService.save(address), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Address> update(@PathVariable("id") long id,
			@RequestBody Address address) throws RestApiException {
		return new ResponseEntity<>(this.addressService.edit(id, address), HttpStatus.OK);
	}
	
}
