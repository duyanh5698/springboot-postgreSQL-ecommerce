package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderDetailsRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;


@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
	public List<Order> getAllOrders(){
		return this.orderRepository.findAll();
	}
		
	public Order save(Order order) {
		Optional<User> user = userRepository.findById(order.getUserId());
		if(user.isPresent()) {
			order.setUser(user.get());
			return orderRepository.save(order);
		}
		return null;
	}
	
	public Boolean deleteById(Long id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isPresent()) {
			orderRepository.deleteById(id);
	        return true;
		}
		return false;
	}
	
	public Order changeStateForOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
        	order.setApproved(!order.isApproved());
            orderRepository.save(order);
            return order;
        }
        return null;
    }
	
	
}
