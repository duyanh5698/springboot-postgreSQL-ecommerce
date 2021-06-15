package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ecommerce.model.AddressEntity;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class AddressService {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<AddressEntity> getAllAddresses(){
		return this.addressRepository.findAll();
	}
	
	public AddressEntity save(AddressEntity addressEntity) {
		Optional<User> user = userRepository.findById(addressEntity.getUserId());
		if(user.isPresent()) {
			addressEntity.setUser(user.get());
			return addressRepository.save(addressEntity);
		}
		return null;
	}
	
	public AddressEntity edit(long id, AddressEntity editAddress) {
		Optional<AddressEntity> addressData = addressRepository.findById(id);
		if(addressData.isPresent()) {
			AddressEntity addressEntity = addressData.get();
			addressEntity.set(editAddress);		
			return addressRepository.save(addressEntity);
		}
		return null;
	}
	
	
	
	
}
