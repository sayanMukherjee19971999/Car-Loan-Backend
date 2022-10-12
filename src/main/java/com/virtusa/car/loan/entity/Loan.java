package com.virtusa.car.loan.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Loan implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int loanId;
	private String loanType;
	
	@NotEmpty
	@Size(min=5,max=20,message="The name should be between 5 to 20 characters")
	@Pattern(regexp="[a-zA-Z][a-zA-Z ]+",message="Enter a valid name")
	private String applicantName;
	
	@NotEmpty
	@Size(min=5,max=50,message="The address should be between 5 to 50 characters")
	@Pattern(regexp="[a-zA-Z]{5,50}",message="Enter a valid address")
	private String applicantAddress;
	
	@NotEmpty
	@Size(min=10,max=10,message="The mobile number should be 10 digits long")
	@Pattern(regexp="[6789]\\d{9}",message="Enter a valid mobile number")
	private String applicantMobile;
	
	@NotEmpty
	@Size(min=12,max=100,message="The email must be between 12 to 100 characters")
	@Pattern(regexp="[a-zA-Z0-9][\\w]{2,}@gmail.com",message="Enter a valid email")
	private String applicantEmail;
	
	@NotEmpty
	@Size(min=12,max=12,message="The aadhar number should be 12 digits long")
	@Pattern(regexp="[5-9][\\d]{11}",message="Enter a valid aadhar number")
	private String applicantAadhar;
	
	@NotEmpty
	@Size(min=10,max=10,message="The pan number must be 10 characters long")
	@Pattern(regexp="[A-Z]{5}[\\d]{4}[A-Z]",message="Enter a valid pan number")
	private String applicantPan;
	
	@NotEmpty
	@Size(min=5,max=6, message="The salary must be between 5-6 digits")
	@Pattern(regexp="[1-9][\\d]{4,5}",message="Enter a valid salary")
	private String applicantSalary;
	
	@NotEmpty
	@Size(min=6,max=8,message="The loan must be between 6-8 digits long")
	@Pattern(regexp="[1-9][\\d]{5,7}",message="Enter a valid loan")
	private String loanAmountRequired;
	
	@NotEmpty
	@Size(min=2,max=3,message="The month must be given greater than equals to 12")
	@Pattern(regexp="[1-9][\\d]{1,2}", message="Enter a valid month")
	private String loanRepaymentMonths;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	@JsonIgnore
	private String fileName;
	
	@JsonIgnore
	private String fileType;
	
	@JsonIgnore
	@Lob
	private byte[] fileData;
	
	private LocalDate loanApplicationDate;
}
