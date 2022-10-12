package com.virtusa.car.loan.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.payloads.ApiResponse;
import com.virtusa.car.loan.service.LoanService;

@RestController
@PreAuthorize("hasRole('NORMAL')")
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	private LoanService loanService;
	
	@GetMapping("/get/{loanId}")
	public ResponseEntity<Loan> getLoanById(@PathVariable("loanId") int id) {
		Loan loanById = this.loanService.getLoanById(id);
		return new ResponseEntity<>(loanById,HttpStatus.OK);
	}
	
	@PutMapping("/update/{loanId}")
	public ResponseEntity<Loan> updateLoan(@Valid @RequestBody Loan loanDto, @PathVariable("loanId") int id){
		Loan updateLoan = this.loanService.updateLoan(loanDto, id);
		return new ResponseEntity<>(updateLoan,HttpStatus.OK);
	}
	
	@PostMapping(value="/apply", consumes ={MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Loan> createLoan(@Valid @RequestPart("loan") String loan, @RequestPart("file") MultipartFile file){
		Loan createLoan = this.loanService.createLoan(loan,file);
		return new ResponseEntity<>(createLoan,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{loanId}")
	public ResponseEntity<ApiResponse> deleteLoan(@PathVariable("loanId") int id){
		this.loanService.deleteLoan(id);
		return new ResponseEntity<>(new ApiResponse(String.format("Loan with Loan Id : %s deleted successfully", id),true),HttpStatus.OK);
	}
	
}
