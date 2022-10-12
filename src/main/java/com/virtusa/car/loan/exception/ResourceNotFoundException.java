package com.virtusa.car.loan.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String domainName, String fieldName, String fieldValue) {
		super(String.format("%s not found with %s : %s", domainName,fieldName,fieldValue));
		
	}
}
