package com.cengizhanyavuz.account.repository;

import com.cengizhanyavuz.account.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
