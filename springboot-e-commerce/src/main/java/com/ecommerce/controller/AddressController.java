package com.ecommerce.controller;

import java.io.IOException;
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

import com.ecommerce.model.AddressEntity;
import com.ecommerce.service.AddressService;

@RestController
@RequestMapping("/api")
public class AddressController {
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressEntity>> getAllAddresses(){
		return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
	}
	
	@PostMapping("/addresses")
	public ResponseEntity<AddressEntity> createProduct(@RequestBody AddressEntity addressEntity) throws IOException{
		return new ResponseEntity<>(addressService.save(addressEntity), HttpStatus.CREATED);
	}
	
	@PutMapping("/addresses/{id}")
	public ResponseEntity<AddressEntity> updateProduct(@PathVariable("id") long id, @RequestBody AddressEntity addressEntity){
		return new ResponseEntity<>(this.addressService.edit(id, addressEntity), HttpStatus.OK);
	}

}
