package com.ecommerce.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;	
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.MessageResponse;
import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;


	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size){
		Page<Product> pagePros = productService.getAllProducts(page-1, size);
		List<Product> products = pagePros.getContent();
		
		Map<String, Object> response = new HashMap<>();
		response.put("products", products);
		response.put("currentPage", pagePros.getNumber());
		response.put("totalItems", pagePros.getTotalElements());
		response.put("totalPages", pagePros.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}


	@GetMapping("/{id}")
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") long id) {
		Optional<Product> product = productService.getProductById(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(product);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Product> create(@RequestBody Product product) throws RestApiException {
		return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") long id, @RequestBody Product product)
			throws RestApiException {
		return new ResponseEntity<>(productService.edit(id, product), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) {
		try {
			productService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Deleted!"));
		} catch (Exception e) {
			return ResponseEntity.ok(new MessageResponse("Not Deleted!"));
		}
	}
}
