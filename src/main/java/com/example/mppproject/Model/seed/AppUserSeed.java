package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.HomeProperty;
import com.example.mppproject.Repository.AccountRepository;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Repository.HomePropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppUserSeed implements CommandLineRunner {
    private final AppUserRepository appUserRepository;
    private final AccountRepository accountRepository;
    private final AddressRepository addressRepository;

     public AppUserSeed(AppUserRepository appUserRepository,
                        AccountRepository accountRepository,
                        AddressRepository addressRepository){
        this.appUserRepository = appUserRepository;
        this.accountRepository = accountRepository;
        this.addressRepository = addressRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if (appUserRepository.count() == 0) {
            AppUser data1 = new AppUser("Nati","nati@gmail.com");
            AppUser data2 = new AppUser("Wende","wende@gmail.com");
            AppUser data3 = new AppUser("Dave","dave@gmail.com");
            AppUser data4 = new AppUser("Zola","zola@gmail.com");
            AppUser data5 = new AppUser("Mike","mike@gmail.com");
            Account account = accountRepository.findById(31L).stream().findFirst().orElse(null);
            data1.setAccount(account);
            Account account2 = accountRepository.findById(32L).stream().findFirst().orElse(null);
            data2.setAccount(account2);
            Account account3 = accountRepository.findById(33L).stream().findFirst().orElse(null);
            data3.setAccount(account3);
            Account account4 = accountRepository.findById(34L).stream().findFirst().orElse(null);
            data4.setAccount(account4);
            Account account5 = accountRepository.findById(35L).stream().findFirst().orElse(null);
            data5.setAccount(account5);

            Address address = addressRepository.findById(5L).stream().findFirst().orElse(null);
            data1.setAddress(address);
            Address address1 = addressRepository.findById(6L).stream().findFirst().orElse(null);
            data2.setAddress(address1);
            Address address2 = addressRepository.findById(7L).stream().findFirst().orElse(null);
            data3.setAddress(address2);
            Address address3 = addressRepository.findById(8L).stream().findFirst().orElse(null);
            data4.setAddress(address3);
            Address address4 = addressRepository.findById(6L).stream().findFirst().orElse(null);
            data5.setAddress(address4);

            appUserRepository.save(data1);
            appUserRepository.save(data2);
            appUserRepository.save(data3);
            appUserRepository.save(data4);
            appUserRepository.save(data5);
        }
    }
}
