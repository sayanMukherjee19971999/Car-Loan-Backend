package com.virtusa.car.loan.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmiResponse {

	private String loanAmount;
	private String loanRepaymentMonths;
	private String interestRate;
	private String emi;
	private String totalAmountPayable;
}
