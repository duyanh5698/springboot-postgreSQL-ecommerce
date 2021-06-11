package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;


@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<Product> getAllProducts(){
		return this.productRepository.findAll();
	}
	
	public Product save(Product product) {
		Optional<User> user = userRepository.findById(product.getUserId());
		if(user.isPresent()) {
			product.setUser(user.get());
			return productRepository.save(product);
		}
		return null;
	}
	
	public Product edit(long id, Product editProduct) {
		Optional<Product> productData = productRepository.findById(id);
		Product product = productData.get();
		//4
		
//		product.set(editProduct);
		product.setName(editProduct.getName());
		product.setDecription(editProduct.getDecription());
		product.setQuantity(editProduct.getQuantity());
		product.setPrice(editProduct.getPrice());
		return productRepository.save(product);
	}
	
	public Boolean deleteById(Long id) {
		//exist
        productRepository.deleteById(id);
        return true;
	}
}
