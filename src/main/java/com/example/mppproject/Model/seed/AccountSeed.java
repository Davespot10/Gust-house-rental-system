
package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Account;

import com.example.mppproject.Repository.AccountRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountSeed implements CommandLineRunner {
    private final AccountRepository accountRepository;
    public AccountSeed(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if (accountRepository.count() == 0) {
            Account data1 = new Account(123,2000.00);
            Account data2 = new Account(456,1000.00);
            Account data3 = new Account(111,1000.00);
            Account data4 = new Account(222,0.00);
            Account data5 = new Account(555,3000.00);

            accountRepository.save(data1);
            accountRepository.save(data2);
            accountRepository.save(data3);
            accountRepository.save(data4);
            accountRepository.save(data5);
        }
    }
}
