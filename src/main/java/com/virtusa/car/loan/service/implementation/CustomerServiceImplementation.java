package com.virtusa.car.loan.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.repository.CustomerRepository;
import com.virtusa.car.loan.service.CustomerService;

@Service
public class CustomerServiceImplementation implements CustomerService {
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	static final String CUSTOMER="Customer";

	@Override
	public List<Customer> getAllCustomers() {
		return this.custRepo.findAll();
	}

	@Override
	public Customer getCustomerById(int id) {
		return this.custRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(CUSTOMER,"Id",String.valueOf(id)));
	}

	@Override
	public Customer updateCustomer(Customer customer, int id) {
		Customer cust=this.custRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(CUSTOMER,"Id",String.valueOf(id)));
		cust.setEmail(customer.getEmail());
		cust.setMobile(customer.getMobile());
		cust.setPassword(this.passwordEncoder.encode(customer.getPassword()));
		cust.setUsername(customer.getUsername());
		return this.custRepo.save(cust);
	}

	@Override
	public void deleteCustomer(int id) {
		Customer cust=this.custRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(CUSTOMER,"Id",String.valueOf(id)));
		this.custRepo.delete(cust);
	}

	@Override
	public Customer registerCustomer(Customer customer) {
		customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
		customer.setRole(new Role("ROLE_NORMAL"));
		return this.custRepo.save(customer);
	}

}
