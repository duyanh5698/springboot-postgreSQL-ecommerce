package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderDetailsRepository;
import com.ecommerce.repository.ProductRepository;

@Service
public class OrderDetailsService {

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	@Autowired
	ProductRepository productRepository;

	public List<OrderDetails> getAllOrderDetails() {
		return this.orderDetailsRepository.findAll();
	}

	public List<OrderDetails> save(List<OrderDetails> orderDetails, Long orderId) throws RestApiException {
		if (orderDetails != null) {
			orderDetails.forEach(i -> {
				Optional<Product> productData = productRepository.findById(i.getProductId());
				if (productData.isPresent()) {
					Product product = productData.get();
					if (product.getQuantity() < i.getQuantity()) {
						throw new RestApiException("Not enough " +product.getName()+ " in stock!");
					}
					else {
						product.setQuantity(product.getQuantity() - i.getQuantity());
						productRepository.save(product);
					}
				}
				i.setOrderId(orderId);
			});
		}
		return orderDetailsRepository.saveAll(orderDetails);
	}

	public Boolean deleteById(Long id) {
		Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
		if (orderDetails.isPresent()) {
			orderDetailsRepository.deleteById(id);
			return true;
		}
		return false;
	}
}
