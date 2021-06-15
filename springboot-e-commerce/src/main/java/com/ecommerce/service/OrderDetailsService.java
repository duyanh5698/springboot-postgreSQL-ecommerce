package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.OrderDetails;
import com.ecommerce.repository.OrderDetailsRepository;

@Service
public class OrderDetailsService {
	
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	
	public List<OrderDetails> getAllOrderDetails(){
		return this.orderDetailsRepository.findAll();
	}
	
	public void save(List<OrderDetails> orderDetails, Long orderId) {
		if(orderDetails != null) {
			orderDetails.forEach(i-> {
				i.setOrderId(orderId);
			});
			orderDetailsRepository.saveAll(orderDetails);
		}
	}
	
	public Boolean deleteById(Long id) {
		Optional<OrderDetails> orderDetails = orderDetailsRepository.findById(id);
		if(orderDetails.isPresent()) {
			orderDetailsRepository.deleteById(id);
	        return true;
		}
		return false;
	}
}
