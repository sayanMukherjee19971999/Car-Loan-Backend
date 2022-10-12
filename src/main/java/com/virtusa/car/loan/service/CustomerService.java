package com.virtusa.car.loan.service;

import java.util.List;

import com.virtusa.car.loan.entity.Customer;

public interface CustomerService {

	public List<Customer> getAllCustomers();
	
	public Customer getCustomerById(int id);
	
	public Customer updateCustomer(Customer customerDto,int id);
	
	public void deleteCustomer(int id);
	
	public Customer registerCustomer(Customer customerDto);
}
