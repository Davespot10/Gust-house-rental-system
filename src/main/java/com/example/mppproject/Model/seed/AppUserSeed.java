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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            AppUser data2 = new AppUser("Wende property","wendeProperty@gmail.com");
            AppUser data3 = new AppUser("Dave","dave@gmail.com");
            AppUser data4 = new AppUser("Zola","zola@gmail.com");
            AppUser data5 = new AppUser("Mike","mike@gmail.com");

            List<Account> account = accountRepository.findAll();
            List<Address> add = addressRepository.findAll();

            List<AppUser> user = new ArrayList<>(Arrays.asList(data1,data2,data3,data5,data4));
            for (int i=0;i<5;i++){
                user.get(i).setAccount(account.get(i));
                user.get(i).setAddress(add.get(i));
                appUserRepository.save(user.get(i));
            }

        }
    }
}
