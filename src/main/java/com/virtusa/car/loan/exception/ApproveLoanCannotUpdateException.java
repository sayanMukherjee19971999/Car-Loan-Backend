package com.virtusa.car.loan.exception;

public class ApproveLoanCannotUpdateException extends RuntimeException {

	public ApproveLoanCannotUpdateException() {
		super("Loan Already Approved. It cannot be updated furthur !!");
	}
	
}
