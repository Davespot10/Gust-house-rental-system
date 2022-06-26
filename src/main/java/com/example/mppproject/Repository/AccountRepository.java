package com.example.mppproject.Repository;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(Integer accountNumber);
}
