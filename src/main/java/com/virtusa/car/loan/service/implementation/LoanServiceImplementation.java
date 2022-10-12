package com.virtusa.car.loan.service.implementation;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.exception.ApproveLoanCannotUpdateException;
import com.virtusa.car.loan.exception.DocumentException;
import com.virtusa.car.loan.exception.ResourceNotFoundException;
import com.virtusa.car.loan.repository.CustomerRepository;
import com.virtusa.car.loan.repository.LoanRepository;
import com.virtusa.car.loan.service.LoanService;

@Service
public class LoanServiceImplementation implements LoanService {
	
	static Logger lOGGER=LoggerFactory.getLogger(LoanServiceImplementation.class);
	
	@Autowired
	private LoanRepository loanRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private EmailService emailService;
	
	static final String LOAN_ID="Loan Id";

	@Override
	public List<Loan> getAllLoans() {
		return this.loanRepo.findAll();
	}

	@Override
	public Loan getLoanById(int id) {
		return this.loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan",LOAN_ID, String.valueOf(id)));
	}

	@Override
	public Loan createLoan(String loan, MultipartFile file) {
		Loan l=new Loan();
		try {
			ObjectMapper objectMapper=new ObjectMapper();
			l = objectMapper.readValue(loan, Loan.class);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		l.setLoanType("pending");
		
		LocalDate date = LocalDate.now();
        
        l.setLoanApplicationDate(date);
		
		if(file.isEmpty()) {
			throw new ResourceNotFoundException("Document","applicant having Email Id",String.valueOf(l.getApplicantEmail()));
		}
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file name contains invalid characters
            if (fileName.contains("..")) {
                throw new DocumentException(fileName);
            }
            l.setFileName(file.getOriginalFilename());
            l.setFileData(file.getBytes());
            l.setFileType(file.getContentType());
            
        } catch (IOException ex) {
            throw new DocumentException(fileName);
        }
        
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer c = custRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("Customer","Email-Id",email));
        
        l.setCustomer(c);
        
		Loan createdLoan=this.loanRepo.save(l);
		
		Map<String,String> loanInfo=new HashMap<>();
		loanInfo.put("LoanID",String.valueOf(createdLoan.getLoanId()));
		loanInfo.put("LoanStatus",createdLoan.getLoanType());
		loanInfo.put("ApplicantName",createdLoan.getApplicantName());
		loanInfo.put("ApplicantAddress",createdLoan.getApplicantAddress());
		loanInfo.put("ApplicantMobile",createdLoan.getApplicantMobile());
		loanInfo.put("ApplicantEmail",createdLoan.getApplicantEmail());
		loanInfo.put("LoanAmountApplied",createdLoan.getLoanAmountRequired());
		loanInfo.put("LoanRepaymentMonths",createdLoan.getLoanRepaymentMonths());
		//String message = loanInfo.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
				//.collect(Collectors.joining("\n"));
		String recipient=createdLoan.getApplicantEmail();
		String subject="Loan Application Information";
		boolean sent=this.emailService.templateEmail(loanInfo, subject, recipient);
		if(sent) {
			lOGGER.info("Loan Apply Email sent successfully");
		}
		else {
			lOGGER.info("Error occured while sending the mail");
		}
		return createdLoan;
	}

	@Override
	public Loan updateLoan(Loan loan, int id) {
		Loan l = this.loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan",LOAN_ID, String.valueOf(id)));
		if(l.getLoanType().equals("approve")) {
			throw new ApproveLoanCannotUpdateException();
		}
		else {
			l.setApplicantEmail(loan.getApplicantEmail());
			l.setApplicantMobile(loan.getApplicantMobile());
			l.setApplicantSalary(loan.getApplicantSalary());
			l.setLoanAmountRequired(loan.getLoanAmountRequired());
			l.setLoanRepaymentMonths(loan.getLoanRepaymentMonths());
		
		
			Map<String,String> loanInfo=new HashMap<>();
			loanInfo.put("LoanID",String.valueOf(id));
			loanInfo.put("LoanStatus",l.getLoanType());
			loanInfo.put("ApplicantName",l.getApplicantName());
			loanInfo.put("ApplicantAddress",l.getApplicantAddress());
			loanInfo.put("ApplicantMobile",loan.getApplicantMobile());
			loanInfo.put("ApplicantEmail",loan.getApplicantEmail());
			loanInfo.put("LoanAmountApplied",loan.getLoanAmountRequired());
			loanInfo.put("LoanRepaymentMonths",loan.getLoanRepaymentMonths());
			//String message = loanInfo.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
				//.collect(Collectors.joining("\n"));
			String recipient=loan.getApplicantEmail();
			String subject="Loan Update Information";
			boolean sent=this.emailService.loanUpdateEmail(loanInfo, subject, recipient);
			if(sent) {
				lOGGER.info("Update Email sent successfully");
			}
			else {
				lOGGER.info("Error occured while sending the mail");
			}
		}
		return this.loanRepo.save(l);
	}

	@Override
	public void deleteLoan(int id) {
		Loan loan = this.loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan",LOAN_ID, String.valueOf(id)));
		this.loanRepo.delete(loan);
	}

	@Override
	public Loan approveRejectLoan(int id, String status) {
		Loan l = this.loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan",LOAN_ID, String.valueOf(id)));
		l.setLoanType(status.toLowerCase());
		this.loanRepo.save(l);
		Map<String,String> loanInfo=new HashMap<>();
		loanInfo.put("LoanID",String.valueOf(id));
		loanInfo.put("LoanStatus",l.getLoanType());
		loanInfo.put("ApplicantName",l.getApplicantName());
		loanInfo.put("ApplicantAddress",l.getApplicantAddress());
		loanInfo.put("ApplicantMobile",l.getApplicantMobile());
		loanInfo.put("ApplicantEmail",l.getApplicantEmail());
		loanInfo.put("LoanAmountApplied",l.getLoanAmountRequired());
		loanInfo.put("LoanRepaymentMonths",l.getLoanRepaymentMonths());
		
		String recipient=l.getApplicantEmail();
		String subject="Loan Status Information";
		
		if(l.getLoanType().equals("approve")) {
			loanInfo.put("Approved", "Your loan has been approved successfully. You will receive a call shortly from out end to take you forward for the furthur steps");
			//String message = loanInfo.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
				//	.collect(Collectors.joining("\n"));
			boolean sent=this.emailService.loanApproveEmail(loanInfo, subject, recipient);
			if(sent) {
				lOGGER.info("Update Email sent successfully");
			}
			else {
				lOGGER.info("Error occured while sending the mail");
			}
		}else if(l.getLoanType().equals("reject")) {
			loanInfo.put("Rejected", "Your loan has been rejected. please provide details correctly and attach proper document proof for furthur application.");
			//String message = loanInfo.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue())
					//.collect(Collectors.joining("\n"));
			boolean sent=this.emailService.loanRejectEmail(loanInfo, subject, recipient);
			if(sent) {
				lOGGER.info("Update Email sent successfully");
			}
			else {
				lOGGER.info("Error occured while sending the mail");
			}
		}
		return l;
	}

}
