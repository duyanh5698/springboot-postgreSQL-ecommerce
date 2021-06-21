package com.ecommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.RestApiException;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.SecurityContext;

@Service
public class AddressService {

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	UserRepository userRepository;

	public List<Address> getAllAddresses() {
		return this.addressRepository.findAll();
	}

	public Address save(Address addressEntity) throws RestApiException {
		Optional<User> user = userRepository.findById(SecurityContext.getCurrentUserId());
		addressEntity.valid();
		addressEntity.setUser(user.get());
		addressEntity.setUserId(user.get().getId());
		return addressRepository.save(addressEntity);

	}

	public Address edit(long id, Address editAddress) throws RestApiException {
		editAddress.valid();
		Optional<Address> addressData = addressRepository.findById(id);
		if (addressData.isPresent()) {
			Address addressEntity = addressData.get();
			addressEntity.set(editAddress);
			return addressRepository.save(addressEntity);
		}
		throw new RestApiException("Address is not exsist in the database!");
	}

}
