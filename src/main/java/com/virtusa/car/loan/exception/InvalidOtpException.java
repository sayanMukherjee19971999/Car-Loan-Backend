package com.virtusa.car.loan.exception;

public class InvalidOtpException extends RuntimeException {
	public InvalidOtpException() {
		super("OTP INVALID!!! ");
	}
}
