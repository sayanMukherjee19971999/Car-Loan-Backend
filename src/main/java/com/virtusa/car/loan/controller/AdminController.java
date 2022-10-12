package com.virtusa.car.loan.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.payloads.ApiResponse;
import com.virtusa.car.loan.repository.LoanRepository;
import com.virtusa.car.loan.service.CustomerService;
import com.virtusa.car.loan.service.LoanService;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private LoanService loanService;
	
	@Autowired
	private LoanRepository loanRepo;
	
	@Autowired
	private CustomerService custService;
	
	@PutMapping("/update/{loanId}/{status}")
	public ResponseEntity<Loan> approveRejectLoan(@PathVariable("loanId") int id, @PathVariable("status") String status){
		Loan approveRejectLoan = this.loanService.approveRejectLoan(id, status);
		return new ResponseEntity<>(approveRejectLoan,HttpStatus.OK);
	}
	
	@GetMapping("/download/{loanId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("loanId") int id) {
        Loan loan = this.loanService.getLoanById(id);
        
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(loan.getFileType()))
        		.header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+loan.getFileName()
        		+"\"").body(new ByteArrayResource(loan.getFileData()));
    }
	
	@GetMapping("/all-loans")
	public ResponseEntity<List<Loan>> viewAllAppliedLoans(){
		List<Loan> allLoans = this.loanService.getAllLoans();
		if(allLoans.isEmpty()) {
			return new ResponseEntity<>(allLoans,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(allLoans,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{loanId}")
	public ResponseEntity<ApiResponse> deleteLoan(@PathVariable("loanId") int id){
		Loan loan = this.loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan", "Loan Id", String.valueOf(id)));
		LocalDate currentDate=LocalDate.now();
		LocalDate loanApplicationDate=loan.getLoanApplicationDate();
		//subtract days from the current date  
		LocalDate newDate = currentDate.minusDays(1);
		int val=newDate.compareTo(loanApplicationDate);
		boolean flag=false;
		if(val>0) {
			flag=true;
		}else if(val<0) {
			flag=false;
		}
		else {
			flag=true;
		}
		if(flag==true && loan.getLoanType().equals("reject")) {
			this.loanRepo.delete(loan);
			return new ResponseEntity<>(new ApiResponse(String.format("Loan with Loan Id : %s deleted successfully", id),true),HttpStatus.OK);
		}
		return new ResponseEntity<>(new ApiResponse("Admin can delete rejected loans only after 7 days of applying. Pending and approved loans cannot be deleted.",false),HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/showall")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		List<Customer> allCustomers = this.custService.getAllCustomers();
		if(allCustomers.isEmpty())
			return new ResponseEntity<>(allCustomers,HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(allCustomers,HttpStatus.OK);
	}
	
}
