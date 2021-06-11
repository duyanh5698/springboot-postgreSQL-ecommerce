package com.ecommerce.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.ecommerce.model.ERole;
import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.model.dto.JwtResponse;
import com.ecommerce.model.dto.LoginRequest;
import com.ecommerce.model.dto.MessageResponse;
import com.ecommerce.model.dto.SignupRequest;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.jwt.JwtUtils;
import com.ecommerce.service.UserService;



@CrossOrigin(origins = "", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		User user = userService.findByUsername(loginRequest.getUsername());
		if(user == null) throw new RestClientException("not found user");
		
		
		if(!encoder.matches(loginRequest.getPassword(), user.getPassword()))
			throw new RestClientException("invalid data input");
		
		String token = jwtUtils.generateJwtTokenByUsername(user.getUsername());
		return ResponseEntity.ok(new JwtResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getAge(), user.getRoles()));
	}
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getAge(),encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		
		Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(() -> new RuntimeException("Error: There is no Role_USER in the database."));
		roles.add(userRole);
		
		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

}
