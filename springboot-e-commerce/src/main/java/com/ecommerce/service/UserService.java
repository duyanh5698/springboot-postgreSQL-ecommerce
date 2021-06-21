package com.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public User findByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);
		return user.isPresent() ? user.get() : null;
	}

}
