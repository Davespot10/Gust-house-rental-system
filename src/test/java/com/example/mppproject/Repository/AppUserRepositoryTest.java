package com.example.mppproject.Repository;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.Role;
import com.example.mppproject.Model.seed.PropertySeed;
import com.example.mppproject.Service.AppUserService;
import com.example.mppproject.utility.RandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        Account account=new Account(RandomGenerator.generateAccount(),672.2);
        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role=new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser=new AppUser(RandomGenerator.generateName(5),RandomGenerator.generateName(5),RandomGenerator.generateEmail(),role,"123456",address,account);
        Mockito.when(appUserRepository.save(appUser)).thenReturn(appUser);
        assertEquals(appUser,appUserService.addAppUser(appUser));
    }
    @Test
    public void getAppUser(){
        Account account=new Account(RandomGenerator.generateAccount(),672.2);
        Address address=new Address("Addis Ababa","Addis Ababa","Ethiopia","1234","5678","19.2","38.9");
        Set<Role> role=new HashSet<>();
        role.add(new Role(RoleType.GUEST));
        AppUser appUser=new AppUser("Dawit","Demelash", RandomGenerator.generateEmail(),role,"123456",address,account);
        Mockito.when(appUserRepository.findAll()).thenReturn(Stream.of(appUser).collect(Collectors.toList()));
        assertEquals(1,appUserService.getAppUser().size());

    }
    @Test
    void otherContextLoads() {
    }


}