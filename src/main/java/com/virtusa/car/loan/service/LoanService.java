package com.virtusa.car.loan.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.car.loan.entity.Loan;

public interface LoanService {

	public List<Loan> getAllLoans();
	
	public Loan getLoanById(int id);
	
	public Loan createLoan(String loan, MultipartFile file);
	
	public Loan updateLoan(Loan loanDto, int id);
	
	public void deleteLoan(int id);
	
	public Loan approveRejectLoan(int id,String status);
	
}
