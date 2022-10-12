package com.virtusa.car.loan.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.exception.PasswordMismatchException;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.payloads.JwtAuthRequest;
import com.virtusa.car.loan.payloads.JwtAuthResponse;
import com.virtusa.car.loan.repository.CustomerRepository;
import com.virtusa.car.loan.security.CustomUserDetailsService;
import com.virtusa.car.loan.security.JwtTokenHelper;
import com.virtusa.car.loan.service.CustomerService;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerRepository custRepo;
	
	static Logger lOGGER=LoggerFactory.getLogger(AuthController.class);
	

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest jwtAuthRequest){
		//authenticating the username and password
		this.authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
		
		//once authenticated we need to generate the token. For this we need to pass user details
		UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		
		Customer customer = custRepo.findByEmail(jwtAuthRequest.getUsername()).orElseThrow(()->new ResourceNotFoundException("Customer","Email",jwtAuthRequest.getUsername()));
		
		//now generate the token with the help of this userdetails
		String token=this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse jwtAuthResponse=new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		jwtAuthResponse.setCustomer(customer);
		
		return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Customer> registerEmployee(@Valid @RequestBody Customer customer) {
		Customer registerCustomer = this.customerService.registerCustomer(customer);
		return new ResponseEntity<>(registerCustomer,HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e) {
			lOGGER.info("Invalid password");
			throw new PasswordMismatchException("Password","Email",password,username);
		}
	}
}
