package com.cengizhanyavuz.account;

import com.cengizhanyavuz.account.model.Customer;
import com.cengizhanyavuz.account.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication
public class AccountApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;

	public AccountApplication(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Bean
	public Clock clock() {
		return Clock.systemUTC();
	}

	@Override
	public void run(String... args)  {
		Customer customer = customerRepository.save(new Customer("Cengizhan", "Yavuz"));
		Customer customer2 = customerRepository.save(new Customer("Test", "test"));

		System.out.println(customer);
		System.out.println(customer2);

	}

}