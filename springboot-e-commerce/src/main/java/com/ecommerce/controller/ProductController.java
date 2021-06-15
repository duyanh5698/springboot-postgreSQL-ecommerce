package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.Product;
import com.ecommerce.model.dto.MessageResponse;
import com.ecommerce.service.FileService;
import com.ecommerce.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
    FileService fileService;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		productService.getAllProducts().forEach(products::add);
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<Optional<Product>> getProductById(Long id){
		Optional<Product> product = productService.getProductById(id);
		if (product.isPresent()) {
			return ResponseEntity.ok(product);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/products")
	public ResponseEntity<String> createProduct(@RequestParam("name") String name,@RequestParam("decription") String decription,@RequestParam("quantity") int quantity,@RequestParam("price") int price,@RequestParam("userId") Long userId, @RequestParam("file") MultipartFile file) throws IOException{
		try {
			Product product = new Product(name,decription,quantity,price,userId);
			productService.save(product);
			fileService.save(file, product.getId());
			return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Product created successfully!"));
		} catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(String.format("Fail to create product!"));
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product){
		return new ResponseEntity<>(this.productService.edit(id, product), HttpStatus.OK);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
		try {
			productService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
