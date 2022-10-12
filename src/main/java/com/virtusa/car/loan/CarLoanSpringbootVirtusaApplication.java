package com.virtusa.car.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.virtusa.car.loan.entity.Customer;
import com.virtusa.car.loan.entity.Role;
import com.virtusa.car.loan.repository.CustomerRepository;


@EnableCaching
@SpringBootApplication
public class CarLoanSpringbootVirtusaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CarLoanSpringbootVirtusaApplication.class, args);
	}
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private CustomerRepository custRepo;

	@Override
	public void run(String... args) throws Exception {
//		Customer customer=new Customer();
//		customer.setEmail("admin@gmail.com");
//		customer.setMobile("7319822803");
//		customer.setPassword(pwdEncoder.encode("admin123@"));
//		customer.setUsername("admin");
//		customer.setRole(new Role("ROLE_ADMIN"));
//		
//		this.custRepo.save(customer);
	}

}
