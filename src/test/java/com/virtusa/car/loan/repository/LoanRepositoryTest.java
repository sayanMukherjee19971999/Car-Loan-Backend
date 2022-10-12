package com.virtusa.car.loan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoanRepositoryTest {

	@Autowired
	private LoanRepository loanRepo;
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Test
	void saveTest() {
		//given
		Loan loan=new Loan();
		Role role=new Role();

		role.setRoleName("ROLE_ADMIN");
		Customer user=new Customer();
		user.setEmail("sayan@gmail.com");
		user.setUsername("sayan");;
		user.setPassword("sayan123");
		user.setMobile("6521478523");
		user.setRole(role);
		custRepo.save(user);

		loan.setApplicantAadhar("745212563589");
		loan.setApplicantAddress("Kolkata");
		loan.setApplicantEmail("abc@gmail.com");
		loan.setApplicantMobile("6325478512");
		loan.setApplicantName("Rahul Roy");
		loan.setApplicantPan("EDFHB0145J");
		loan.setApplicantSalary("45000");
		loan.setFileName("spring.jpg");
		loan.setFileType("image/jpg");
		loan.setLoanAmountRequired("500000");
		loan.setLoanRepaymentMonths("120");
		loan.setLoanType("pending");
		loan.setCustomer(user);
		
		//when
		Loan l=loanRepo.save(loan);
		
		//then
		assertThat(l.getCustomer()).isEqualTo(user);
	}
	
	@Test
	void findByIdTest() {
		//given
		int id=1;
		
		//when
		Loan loan=loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan","Loan Id",String.valueOf(id)));
		
		//then
		assertThat(loan.getLoanId()).isEqualTo(id);
	}
	
	@Test
	void findAllTest() {
		//when
		List<Loan> loans=loanRepo.findAll();
		
		//then
		assertThat(loans.size()).isEqualTo(1);
	}
	
	@Test
	void deleteTest() {
		//given
		int id=1;
		
		//when
		Loan loan=loanRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Loan","Loan Id",String.valueOf(id)));
		loanRepo.delete(loan);
		List<Loan> loans=loanRepo.findAll();
		
		//then
		assertThat(loans.size()).isEqualTo(0);
	}
}
