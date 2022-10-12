package com.virtusa.car.loan.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.repository.CustomerRepository;
import com.virtusa.car.loan.user.details.implementation.UserDetailsImplementation;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer=this.customerRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("Customer","Email Id",username));
		return new UserDetailsImplementation(customer);
	}

}
