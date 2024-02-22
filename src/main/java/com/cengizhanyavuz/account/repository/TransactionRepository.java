package com.cengizhanyavuz.account.repository;

import com.cengizhanyavuz.account.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
