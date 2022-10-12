package com.virtusa.car.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.virtusa.car.loan.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

}
