package com.virtusa.car.loan.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.exception.ResourceNotFoundException;


@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository custRepo;
	
	
	@Test
	void findByEmailTest() {
		//given
		String email="sayan@gmail.com";
		String name="Sayan";
		String password="sayan123";
		String mobile="6547125395";
		Role role=new Role();

		role.setRoleName("ROLE_NORMAL");
		Customer user=new Customer();

		user.setEmail(email);
		user.setUsername(name);;
		user.setPassword(password);
		user.setMobile(mobile);
		user.setRole(role);
		custRepo.save(user);
		
		//when
		Optional<Customer> userByEmail = custRepo.findByEmail(email);
		
		//then
		assertThat(userByEmail.get().getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	void userDoesNotExistByEmailTest() {
		//given
		String email="abc@gmail.com";
		
		//when
		Optional<Customer> userByEmail = custRepo.findByEmail(email);
		
		//then
		assertEquals(Optional.empty(),userByEmail);
	}
	
	@Test
	void saveTest() {
		//given
		String email="sayak@gmail.com";
		String name="Sayak";
		String password="sayak123";
		String mobile="7027125395";
		Role role=new Role();

		role.setRoleName("ROLE_ADMIN");
		Customer user=new Customer();

		user.setEmail(email);
		user.setUsername(name);;
		user.setPassword(password);
		user.setMobile(mobile);
		user.setRole(role);
		
		//when
		Customer cust=custRepo.save(user);
		
		//then
		assertThat(cust.getEmail()).isEqualTo(user.getEmail());
	}
	
	@Test
	void findByIdTest() {
		//given
		int id=2;
		
		//when
		Customer user=custRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer","Customer Id",String.valueOf(id)));
		
		//then
		assertThat(user.getCustId()).isEqualTo(id);
	}
	
	@Test
	void findAllTest() {
		//when
		List<Customer> customers=custRepo.findAll();
		
		//then
		assertThat(customers.size()).isEqualTo(2);
	}
	
	@Test
	void deleteTest() {
		//given
		int id=1;
		
		//when
		Customer user=custRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Customer","Customer Id",String.valueOf(id)));
		custRepo.delete(user);
		List<Customer> users=custRepo.findAll();
		
		//then
		assertThat(users.size()).isEqualTo(1);
	}
}
