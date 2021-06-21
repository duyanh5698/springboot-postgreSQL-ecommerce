package com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.SecurityContext;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	UserRepository userRepository;

	
	public Page<Product> getAllProducts(int page, int size){
		Pageable paging = PageRequest.of(page, size);
		return productRepository.findAll(paging);
	}

	public Optional<Product> getProductById(Long id) {
		return this.productRepository.findById(id);
	}

	public Product save(Product product) throws RestApiException {
		Optional<User> user = userRepository.findById(SecurityContext.getCurrentUserId());
		product.setUser(user.get());
		product.setUserId(user.get().getId());
		product.valid();
		return productRepository.save(product);
	}

	public Product edit(long id, Product editProduct) throws RestApiException {
		editProduct.valid();
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isPresent()) {
			Product product = productData.get();
			product.set(editProduct);
			product.setUserId(SecurityContext.getCurrentUserId());
			return productRepository.save(product);
		}
		throw new RestApiException("Product is not exsist in the database!");
	}

	public Boolean deleteById(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
