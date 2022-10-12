package com.virtusa.car.loan.exception;

public class DocumentException extends RuntimeException {

	public DocumentException(String fileName) {
		super(String.format("File not uploaded with filename : %s",fileName));
	}
	
}
