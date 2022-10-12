package com.virtusa.car.loan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.payloads.ApiResponse;
import com.virtusa.car.loan.service.CustomerService;
import com.virtusa.car.loan.service.implementation.ForgotPasswordService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService custService;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/get/{custId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("custId") int id){
		Customer customerById = this.custService.getCustomerById(id);
		return new ResponseEntity<>(customerById,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@PutMapping("/update/{custId}")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody Customer custDto, @PathVariable("custId") int id){
		Customer updateCustomer = this.custService.updateCustomer(custDto, id);
		return new ResponseEntity<>(updateCustomer,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@DeleteMapping("/delete/{custId}")
	public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable("custId") int id){
		this.custService.deleteCustomer(id);
		return new ResponseEntity<>(new ApiResponse(String.format("Customer with Id : %s deleted successfully", id),true),HttpStatus.OK);
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@RequestParam("email") String email){
		this.forgotPasswordService.forgotPassword(email);
		return new ResponseEntity<>("OTP sent to the registered Email Id", HttpStatus.OK);
	}
	
	@PostMapping("/validate-otp")
	public ResponseEntity<String> validateOtp(@RequestParam("otp") String otp, @RequestParam("password") String password){
		this.forgotPasswordService.validateOtp(otp,password);
		return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
	}
	
}
