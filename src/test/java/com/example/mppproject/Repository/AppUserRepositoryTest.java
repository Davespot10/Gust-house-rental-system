package com.example.mppproject.Repository;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.Role;
import com.example.mppproject.Service.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)

class AppUserRepositoryTest {
    @Autowired
    private AppUserService appUserService;
    @MockBean
    private AppUserRepository appUserRepository;
    @Test
    public void addAppUserTest(){
        Account account=new Account(12346898,672.2);
        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role=new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser=new AppUser("Dawit","Demelash","davespot10",role,"123456",address,account);
        Mockito.when(appUserRepository.save(appUser)).thenReturn(appUser);
        assertEquals(appUser,appUserService.addAppUser(appUser));
    }
    @Test
    void otherContextLoads() {
    }


}