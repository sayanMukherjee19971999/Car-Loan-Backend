package com.virtusa.car.loan.exception;

public class FileExtensionMismatchException extends RuntimeException {
	public FileExtensionMismatchException() {
		super("The chosen file extension and the uploaded file extension does't match");
	}
}
