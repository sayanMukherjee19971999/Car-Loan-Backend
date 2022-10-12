package com.virtusa.car.loan.payloads;

import com.virtusa.car.loan.entity.Customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse {

	private String token;
	
	private Customer customer;
}
