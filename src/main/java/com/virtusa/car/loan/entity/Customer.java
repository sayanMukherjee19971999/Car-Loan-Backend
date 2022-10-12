package com.virtusa.car.loan.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int custId;
	
	@NotEmpty
	@Size(min=12,max=100,message="The email must be between 12 to 100 characters")
	@Pattern(regexp="[a-zA-Z0-9][\\w]{2,}@gmail.com",message="Enter a valid email")
	@Column(unique=true)
	private String email;
	
	@NotEmpty
	@Size(min=5,max=20, message="The username must be between 5 to 20 characters long")
	@Pattern(regexp="[a-zA-Z0-9]{5,20}",message="Enter a valid username")
	private String username;
	
	@NotEmpty
	@Size(min=10,max=10,message="The mobile number should be 10 digits long")
	@Pattern(regexp="[6789]\\d{9}",message="Enter a valid mobile number")
	private String mobile;
	
	@NotEmpty
	@Size(min=8,message="The password should be greater than 8 characters")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
	@JsonManagedReference
	@OneToMany(cascade=CascadeType.ALL,mappedBy="customer", orphanRemoval = true)
	private List<Loan> loan;

	public Customer(
			@NotEmpty @Size(min = 12, max = 100, message = "The email must be between 12 to 100 characters") @Pattern(regexp = "[a-zA-Z0-9][\\w]{2,}@gmail.com", message = "Enter a valid email") String email,
			@NotEmpty @Size(min = 5, max = 20, message = "The username must be between 5 to 20 characters long") @Pattern(regexp = "[a-zA-Z0-9]{5,20}", message = "Enter a valid username") String username,
			@NotEmpty @Size(min = 10, max = 10, message = "The mobile number should be 10 digits long") @Pattern(regexp = "[6789]\\d{9}", message = "Enter a valid mobile number") String mobile,
			@NotEmpty @Size(min = 8, message = "The password should be greater than 8 characters") String password,
			Role role, List<Loan> loan) {
		this.email = email;
		this.username = username;
		this.mobile = mobile;
		this.password = password;
		this.role = role;
		this.loan = loan;
	}
	
	
}
