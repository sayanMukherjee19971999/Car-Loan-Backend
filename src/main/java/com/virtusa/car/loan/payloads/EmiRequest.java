package com.virtusa.car.loan.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmiRequest {

	@NotEmpty
	@Size(min=6,max=8,message="The loan amount must be between lakhs to crore")
	@Pattern(regexp="[1-9][\\d]{5,7}",message="Enter a valid Loan Amount")
	private String loanAmount;
	
	@NotEmpty
	@Size(min=2,max=3,message="The loan repayment months must be of 2 to 3 digits")
	@Pattern(regexp="[1-9][\\d]{1,2}",message="Enter a valid month")
	private String loanRepaymentMonths;
	
	@NotEmpty
	@Pattern(regexp="[1-9][\\d]{1}[.]*[\\d]{1,2}",message="Enter a valid interest rate")
	private String interestRate;
}
