package com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderDetailsRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	OrderDetailsRepository orderDetailsRepository;

	public Page<Order> getAllOrders(int page, int size){
		Pageable paging = PageRequest.of(page, size);
		return orderRepository.findAll(paging);
	}

	public Order save(Order order) throws RestApiException {
		return orderRepository.save(order);
	}

	public Boolean deleteById(Long id) throws RestApiException {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			orderRepository.deleteById(id);
			return true;
		}
		throw new RestApiException("Order is not exsist in the database!");
	}

	public Order changeStateForOrder(Long id) throws RestApiException {
		Order order = orderRepository.findById(id).orElse(null);
		if (order != null) {
			order.setApproved(!order.isApproved());
			orderRepository.save(order);
			return order;
		}
		throw new RestApiException("Order is not exsist in the database!");
	}

}
