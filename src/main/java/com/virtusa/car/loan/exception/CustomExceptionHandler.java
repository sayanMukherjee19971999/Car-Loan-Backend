package com.virtusa.car.loan.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.virtusa.car.loan.payloads.ApiResponse;

@RestControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
		String message=ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ApproveLoanCannotUpdateException.class)
	public ResponseEntity<ApiResponse> approveLoanCannotUpdateException(ApproveLoanCannotUpdateException ex){
		String message=ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleMethodArdNotValidException(MethodArgumentNotValidException ex){
	        Map<String, String> message = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach(error->{
	            String fieldName=((FieldError)error).getField();
	            String fieldMessage = error.getDefaultMessage();
	            message.put(fieldName, fieldMessage);
	        });
	        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex){
		 
		 Map<String, String> message = new HashMap<>();
		 ex.getConstraintViolations().stream().forEach(error->{
			 String fieldName=null;
			 for (Node node : error.getPropertyPath()) {
	                fieldName = node.getName();
	         }
			 String fieldMessage=error.getMessage();
			 message.put(fieldName, fieldMessage);
		 })	;
		 
		 return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	    }
	 
	 
	 @ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<ApiResponse> passwordMismatchException(PasswordMismatchException ex){
		String message=ex.getMessage();
		return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
	}
	 
	 @ExceptionHandler(DocumentException.class)
		public ResponseEntity<ApiResponse> documentException(DocumentException ex){
			String message=ex.getMessage();
			return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	 
	 @ExceptionHandler(FileExtensionMismatchException.class)
		public ResponseEntity<ApiResponse> documentException(FileExtensionMismatchException ex){
			String message=ex.getMessage();
			return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
		}
	 
	 @ExceptionHandler(MaxUploadSizeExceededException.class)
	 public  ResponseEntity<ApiResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException ex){
		 String message="The file you uploaded exceeded the maximum limit of 2MB. Please upload file within 2MB";
		 return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
	 }
	 
	 @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
		public ResponseEntity<ApiResponse> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
			String message="Email already exists!! Please login to access the application...";
			return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
		}
	 
	 @ExceptionHandler(InvalidOtpException.class)
		public ResponseEntity<ApiResponse> invalidOtpException(InvalidOtpException ex){
			String message=ex.getMessage();
			return new ResponseEntity<>(new ApiResponse(message,false),HttpStatus.BAD_REQUEST);
		}
}
