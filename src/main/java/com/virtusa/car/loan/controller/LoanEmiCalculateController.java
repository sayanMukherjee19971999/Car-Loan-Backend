package com.virtusa.car.loan.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.virtusa.car.loan.payloads.EmiRequest;
import com.virtusa.car.loan.payloads.EmiResponse;

@RestController
@RequestMapping("/calculate")
public class LoanEmiCalculateController {

	@GetMapping("/emi")
	public ResponseEntity<EmiResponse> calculateEmi(@Valid @RequestBody EmiRequest emiRequest){
		long principalAmount=Long.parseLong(emiRequest.getLoanAmount());
		int numberOfMonths=Integer.parseInt(emiRequest.getLoanRepaymentMonths());
		double rateOfInterest=Double.parseDouble(emiRequest.getInterestRate());
		
		double monthlyRateOfInterest=rateOfInterest/1200;
		
		double monthlyEmi=(principalAmount*monthlyRateOfInterest*Math.pow((1+monthlyRateOfInterest),numberOfMonths))/(Math.pow((1+monthlyRateOfInterest),(numberOfMonths))-1);
		
		long a =Math.round(monthlyEmi);
		
		long totalAmountPayable=a*numberOfMonths;
		
		EmiResponse emiResponse=new EmiResponse();
		
		emiResponse.setInterestRate(String.valueOf(rateOfInterest));
		emiResponse.setLoanAmount(String.valueOf(principalAmount));
		emiResponse.setLoanRepaymentMonths(String.valueOf(numberOfMonths));
		emiResponse.setEmi(String.valueOf(a));
		emiResponse.setTotalAmountPayable(String.valueOf(totalAmountPayable));
		
		return new ResponseEntity<>(emiResponse,HttpStatus.OK);
	}
}
