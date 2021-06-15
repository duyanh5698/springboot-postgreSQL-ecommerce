package com.ecommerce.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.dto.MessageResponse;
import com.ecommerce.service.OrderDetailsService;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailsService orderDetailsService;
	
	@GetMapping("/orders")
	public  ResponseEntity<List<Order>> getAllOrders(){
		return ResponseEntity.ok(this.orderService.getAllOrders());
	}
	
	@PostMapping("/orders")
	public ResponseEntity<?> createOrder(@RequestBody Order order) throws IOException{
		List<OrderDetails> orderDetails = order.getOrderDetails();
		order = orderService.save(order);
		orderDetailsService.save(orderDetails, order.getId());
		return ResponseEntity.ok(new MessageResponse("Order successfully!"));
	}
	
	@DeleteMapping("/orders/delete/{id}")
	public ResponseEntity<MessageResponse> deleteProduct(@PathVariable("id") long id) {
		try {
			orderService.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("Deleted!"));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/orders/state/{id}")
    public ResponseEntity<Order> changeDoneState(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.changeStateForOrder(id));
    }
}
