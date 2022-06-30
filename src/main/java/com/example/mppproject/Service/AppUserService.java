package com.example.mppproject.Service;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Repository.AccountRepository;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.exceptionResponse.userException.UserBadRequestException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;

    public AppUserService(AppUserRepository appUserRepository, AddressRepository addressRepository, AccountRepository accountRepository) {
        this.appUserRepository = appUserRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
    }

//    Admin Authenticated
    public List<AppUser> getAppUser() {
        return appUserRepository.findAll();
    }

//    Not Authenticated and Authorized
    public AppUser addAppUser(AppUser appUser) {
        Optional<AppUser> usernameEntry = appUserRepository.findByUserName(appUser.getUserName());
        if (usernameEntry.isPresent()) {
            throw new UserBadRequestException("Username already exists!");
        }
        Integer accountNumberFromUser = appUser.getAccount().getAccountNumber();
        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountNumberFromUser);
        if (accountNumber.isPresent()) {
            throw new UserBadRequestException("accountNumber already exists!");
        }

        Address address = addressRepository.save(appUser.getAddress());
        Account account = accountRepository.save(appUser.getAccount());
        appUser.setAddress(address);
        appUser.setAccount(account);
        return appUserRepository.save(appUser);

    }

//    Admin and Authenticated
    public Optional<AppUser> editAppUserAccount(AppUser appUser) throws RuntimeException {
        Long userId = appUser.getId();
        Optional<AppUser> existingUser = appUserRepository.findById(userId);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("user id :" + userId + " does not exist");
        }
        AppUser appUser1 = existingUser.get();

        Integer accountNumberFromUser = appUser.getAccount().getAccountNumber();
        Double balanceNumberFromUser = appUser.getAccount().getBalance();
        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountNumberFromUser);
        if (accountNumber.isPresent()) {
            Double newAccountNumber = balanceNumberFromUser + accountNumber.get().getBalance();
            appUser1.getAccount().setBalance(newAccountNumber);


            appUserRepository.save(appUser1);
        }

        return appUserRepository.findById(userId);
    }






}
