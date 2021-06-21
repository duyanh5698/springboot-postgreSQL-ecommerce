package com.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.service.OrderDetailsService;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailsService orderDetailsService;

	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> list(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "3") int size) {
		Page<Order> pagePros = orderService.getAllOrders(page - 1, size);
		List<Order> products = pagePros.getContent();

		Map<String, Object> response = new HashMap<>();
		response.put("products", products);
		response.put("currentPage", pagePros.getNumber());
		response.put("totalItems", pagePros.getTotalElements());
		response.put("totalPages", pagePros.getTotalPages());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Order order) throws RestApiException {
		List<OrderDetails> orderDetails = order.getOrderDetails();
		if (!orderDetails.isEmpty()) {
			order = orderService.save(order);
			orderDetailsService.save(orderDetails, order.getId());
			return ResponseEntity.ok(new MessageResponse("Order successfully!"));
		}
		return ResponseEntity.ok(new MessageResponse("Order is not created!"));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) throws RestApiException {
		orderService.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Deleted!"));
	}

	@PutMapping("/approve/{id}")
	public ResponseEntity<Order> changeDoneState(@PathVariable Long id) throws RestApiException {
		return ResponseEntity.ok(orderService.changeStateForOrder(id));
	}
}
