package com.example.mppproject.Service;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Repository.AccountRepository;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import com.google.api.gax.rpc.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

    public List<AppUser> getAppUser() {
        return appUserRepository.findAll();
    }

    public void addAppUser(AppUser appUser) {
        Optional<AppUser> usernameEntry = appUserRepository.findByUserName(appUser.getUserName());
        if(usernameEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        Integer accountNumberFromUser = appUser.getAccount().getAccountNumber();
        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountNumberFromUser);
        if(accountNumber.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accountNumber already exists!");
        }

        Address address = addressRepository.save(appUser.getAddress());
        Account account = accountRepository.save(appUser.getAccount());
        appUser.setAddress(address);
        appUser.setAccount(account);
        appUserRepository.save(appUser);

    }

    public void editAppUserAccount(AppUser appUser) throws Exception {
        Long userId = appUser.getId();
        Optional<AppUser> existingUser = appUserRepository.findById(userId);
        if(!existingUser.isPresent()) {
            throw new UserNotFoundException("student id :" + userId + " does not exist");
        }
        AppUser appUser1 = existingUser.get();

        Integer accountNumberFromUser = appUser.getAccount().getAccountNumber();
        Double balanceNumberFromUser = appUser.getAccount().getBalance();
        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountNumberFromUser);
        if(accountNumber.isPresent()){
            Double newAccountNumber = balanceNumberFromUser + accountNumber.get().getBalance();
            appUser1.getAccount().setBalance(newAccountNumber);


            appUserRepository.save(appUser1);
        }

//        return appUserRepository.findById(userId);
    }
}
