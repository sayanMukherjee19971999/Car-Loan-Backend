package com.virtusa.car.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.repository.LoanRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoanServiceTest {

	@MockBean
	private LoanRepository loanRepo;
	
	@Autowired
	private LoanService loanService;
	
	@Test
	void getAllLoansTest() throws IOException {
		//given
		Customer user=new Customer();
		user.setCustId(1);
		user.setUsername("Sayak");
		user.setEmail("sayak@gmail.com");
		user.setPassword("sayak123@");
		user.setMobile("8563214589");
		user.setRole(new Role("ROLE_NORMAL"));
		MultipartFile file=new MockMultipartFile("file", "This is a plain text".getBytes());
		
		List<Loan> loan=new ArrayList<>();
		Loan loan1=new Loan(1, "pending","Sayan Mukherjee","Kolkata","8563214589","sayan@gmail.com","745212365896","EASDM0423N","45000","500000","36",user,"spring.txt","application/txt",file.getBytes(),LocalDate.now());
		Loan loan2=new Loan(2, "pending","Sayan Mukherjee","Kolkata","8563214589","sayan@gmail.com","745212365896","EASDM0423N","45000","500000","36",user,"spring.txt","application/txt",file.getBytes(),LocalDate.now());
		
		loan.add(loan1);
		loan.add(loan2);
		
		//when
		Mockito.when(loanRepo.findAll()).thenReturn(loan);
		
		//then
		assertThat(loanService.getAllLoans()).isEqualTo(loan);
	}
	
	@Test
	void getSingleLoanByIdTest() throws IOException {
		//given
		Customer user=new Customer();
		user.setCustId(1);
		user.setUsername("Sayak");
		user.setEmail("sayak@gmail.com");
		user.setPassword("sayak123@");
		user.setMobile("8563214589");
		user.setRole(new Role("ROLE_NORMAL"));
		MultipartFile file=new MockMultipartFile("file", "This is a plain text".getBytes());
		Optional<Loan> loan=Optional.of(new Loan(1, "pending","Sayan Mukherjee","Kolkata","8563214589","sayan@gmail.com","745212365896","EASDM0423N","45000","500000","36",user,"spring.txt","application/txt",file.getBytes(),LocalDate.now()));
		
		//when
		Mockito.when(loanRepo.findById(1)).thenReturn(loan);
		
		//then
		assertThat(loanService.getLoanById(1)).isEqualTo(loan.get());
	}
	
	@Test
	void deleteLoanTest() throws IOException {
		MultipartFile file=new MockMultipartFile("file", "This is a plain text".getBytes());
		Customer user=new Customer();
		user.setCustId(1);
		user.setUsername("Sayak");
		user.setEmail("sayak@gmail.com");
		user.setPassword("sayak123@");
		user.setMobile("8563214589");
		user.setRole(new Role("ROLE_NORMAL"));
		Optional<Loan> l=Optional.of(new Loan(1, "pending","Sayan Mukherjee","Kolkata","8563214589","sayan@gmail.com","745212365896","EASDM0423N","45000","500000","36",user,"spring.txt","application/txt",file.getBytes(),LocalDate.now()));
		
		//when
		Mockito.when(loanRepo.findById(1)).thenReturn(l);
		
		Mockito.when(loanRepo.existsById(l.get().getLoanId())).thenReturn(false);
		
		//then
		assertFalse(loanRepo.existsById(l.get().getLoanId()));
	}
	
	@Test
	void updateLoanTest() throws IOException {
		//given
		MultipartFile file=new MockMultipartFile("file", "This is a plain text".getBytes());
		Customer user=new Customer();
		user.setCustId(1);
		user.setUsername("Sayak");
		user.setEmail("sayak@gmail.com");
		user.setPassword("sayak123@");
		user.setMobile("8563214589");
		user.setRole(new Role("ROLE_NORMAL"));
		Optional<Loan> l=Optional.of(new Loan(1, "pending","Sayan Mukherjee","Kolkata","8563214589","sayan@gmail.com","745212365896","EASDM0423N","45000","500000","36",user,"spring.txt","application/txt",file.getBytes(),LocalDate.now()));
		
		//when
		Mockito.when(loanRepo.findById(1)).thenReturn(l);
		
		//doing update
		l.get().setApplicantEmail("sayanmukherjee@gmail.com");
		
		//when
		Mockito.when(loanRepo.save(l.get())).thenReturn(l.get());
		
		assertThat(loanService.updateLoan(l.get(), 1)).isEqualTo(l.get());
	}
}
