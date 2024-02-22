package com.cengizhanyavuz.account.repository;

import com.cengizhanyavuz.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
