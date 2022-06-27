package com.example.mppproject.Repository;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findById(long id);

    Optional<AppUser> findByUserName(String userName);
//    ArrayList<AppUser> findById(long appUserId);
    Optional<Account> findByAccount(Integer accountNumber);
}
