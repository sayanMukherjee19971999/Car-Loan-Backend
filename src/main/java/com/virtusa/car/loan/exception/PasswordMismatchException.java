package com.virtusa.car.loan.exception;

public class PasswordMismatchException extends RuntimeException{

	public PasswordMismatchException(String fieldName1, String fieldName2, String fieldValue1, String fieldValue2) {
		super(String.format("%s : %s does not match with the %s : %s", fieldName1,fieldValue1,fieldName2,fieldValue2));
	}
}
