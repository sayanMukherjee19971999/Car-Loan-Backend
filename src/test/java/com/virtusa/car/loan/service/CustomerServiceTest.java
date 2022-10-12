package com.virtusa.car.loan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Loan;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceTest {

	@MockBean
	private CustomerRepository custRepo;
	
	@Autowired
	private CustomerService custService;
	
	@Test
	void getAllCustomersTest() {
		//given
		List<Loan> loan=new ArrayList<>();
		Customer user1=new Customer("sayan@gmail.com","sayan","8541236589","sayan123",new Role("ROLE_NORMAL"),loan);
		Customer user2=new Customer("srinjoy@gmail.com","srinjoy","8541236589","srinjoy123",new Role("ROLE_ADMIN"),loan);
		
		List<Customer> users=new ArrayList<>();
		users.add(user1);
		users.add(user2);
		
		//when
		Mockito.when(custRepo.findAll()).thenReturn(users);
		
		//then
		assertThat(custService.getAllCustomers()).isEqualTo(users);
	}
	
	@Test
	void getSingleCustomerByIdTest() {
		//given
		List<Loan> loan=new ArrayList<>();
		Optional<Customer> user=Optional.of(new Customer());
		user.get().setCustId(1);
		user.get().setUsername("sayan");
		user.get().setEmail("sayan@gmail.com");
		user.get().setPassword("sayan123@");
		user.get().setMobile("6541258965");
		user.get().setLoan(loan);
		user.get().setRole(new Role("ROLE_NORMAL"));
		
		//when
		Mockito.when(custRepo.findById(1)).thenReturn(user);
		
		//then
		assertThat(custService.getCustomerById(1)).isEqualTo(user.get());
	}
	
	@Test
	void saveUserTest() {
		//given
		List<Loan> loan=new ArrayList<>();
		Customer user=new Customer();
		user.setCustId(1);
		user.setUsername("Sayak");
		user.setEmail("sayak@gmail.com");
		user.setPassword("sayak123@");
		user.setMobile("8563214589");
		user.setLoan(loan);
		user.setRole(new Role("ROLE_NORMAL"));
		
		//when
		Mockito.when(custRepo.save(user)).thenReturn(user);
		
		//then
		assertThat(custService.registerCustomer(user)).isEqualTo(user);
	}
	
	@Test
	void deleteUserTest() {
		//given
		List<Loan> loan=new ArrayList<>();
		Optional<Customer> user=Optional.of(new Customer());
		user.get().setCustId(1);
		user.get().setUsername("Sayan");
		user.get().setEmail("sayan@gmail.com");
		user.get().setPassword("sayan123@");
		user.get().setMobile("8563214589");
		user.get().setLoan(loan);
		user.get().setRole(new Role("ROLE_NORMAL"));
		
		//when
		Mockito.when(custRepo.findById(1)).thenReturn(user);
		
		Mockito.when(custRepo.existsById(user.get().getCustId())).thenReturn(false);
		
		//then
		assertFalse(custRepo.existsById(user.get().getCustId()));
	}
	
	@Test
	void updateUserTest() {
		//given
		Optional<Customer> user=Optional.of(new Customer());
		List<Loan> loan=new ArrayList<>();
		user.get().setCustId(1);
		user.get().setUsername("Sayan");
		user.get().setEmail("sayan@gmail.com");
		user.get().setPassword("sayan123@");
		user.get().setMobile("8563214589");
		user.get().setLoan(loan);
		user.get().setRole(new Role("ROLE_NORMAL"));
		
		//when
		Mockito.when(custRepo.findById(1)).thenReturn(user);
		
		//doing update
		user.get().setEmail("sayanmukherjee@gmail.com");
		
		//when
		Mockito.when(custRepo.save(user.get())).thenReturn(user.get());
		
		assertThat(custService.updateCustomer(user.get(), 1)).isEqualTo(user.get());
	}
}
