package com.example.mppproject.Service;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Repository.AccountRepository;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Repository.RoleRepository;
import com.example.mppproject.exceptionResponse.userException.UserBadRequestException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import com.example.mppproject.utility.RandomGenerator;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public AppUserService(AppUserRepository appUserRepository, AddressRepository addressRepository, AccountRepository accountRepository, RoleRepository roleRepository) {
        this.appUserRepository = appUserRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
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
//        Integer accountNumberFromUser = appUser.getAccount().getAccountNumber();
        Integer accountNumber = RandomGenerator.generateAccount();
        Optional<Account> accountNumberUser = accountRepository.findByAccountNumber(accountNumber);
        if (accountNumberUser.isPresent()) {
            throw new UserBadRequestException("accountNumber already exists!");
        }

        Address address = addressRepository.save(appUser.getAddress());
        Account account1 = new Account();
        account1.setAccountNumber(accountNumber);
        account1.setBalance(0.0);
        Account account = accountRepository.save(account1);

        Set<Role> roles = new HashSet<>();
        for(Role r: appUser.getRoles()){
            Role role = roleRepository.findByRoleType(r.getRoleType());
            roles.add(role);
        }

        appUser.setAddress(address);
        appUser.setAccount(account);
        appUser.setRoles(roles);
        return appUserRepository.save(appUser);
//        return appUser;

    }

//    Admin and Authenticated
    public Optional<AppUser> editAppUserAccount(AppUser appUser) throws RuntimeException {
        Long userId = appUser.getId();
        Optional<AppUser> existingUser = appUserRepository.findById(userId);
        if (!existingUser.isPresent()) {
            throw new UserNotFoundException("user id :" + userId + " does not exist");
        }
        AppUser appUser1 = existingUser.get();

        Integer accountNumberFromUser = appUser1.getAccount().getAccountNumber();
        Double balanceNumberFromUser = appUser1.getAccount().getBalance();
        Optional<Account> accountNumber = accountRepository.findByAccountNumber(accountNumberFromUser);
        if (accountNumber.isPresent()) {
            Double newAccountNumber = balanceNumberFromUser + appUser.getAccount().getBalance();
            appUser1.getAccount().setBalance(newAccountNumber);


            appUserRepository.save(appUser1);
        }

        return appUserRepository.findById(userId);
    }


    public AppUser getAppUserById(long id) {
        AppUser appUser = appUserRepository.findById(id);
        if (appUser.getId() == 0) throw new UserNotFoundException("User Not Found!");
        return appUser;
    }


}
