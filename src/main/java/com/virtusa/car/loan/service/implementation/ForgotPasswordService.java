package com.virtusa.car.loan.service.implementation;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.exception.InvalidOtpException;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.repository.CustomerRepository;

@Service
public class ForgotPasswordService {
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	int otp=0;
	Customer cust=null;

	public void forgotPassword(String email) {
		cust=this.custRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Customer","Email-Id",email));
		 
		//generating random 6-digit number
		otp=ThreadLocalRandom.current().nextInt(100000, 999999);
		this.emailService.email(String.format("The OTP is : %s", String.valueOf(otp)), "OTP - Forgot Password", email);
	}
	
	public void validateOtp(String enteredOtp, String newPassword) {
		if(enteredOtp.equals(String.valueOf(otp))) {
			cust.setPassword(this.passwordEncoder.encode(newPassword));		
			this.custRepo.save(cust);
			this.emailService.email(String.format("Password against email : %s changed successfully", cust.getEmail()), "Alert!! Password Changed", cust.getEmail());
		}
		else {
			throw new InvalidOtpException();
		}
	}
}
