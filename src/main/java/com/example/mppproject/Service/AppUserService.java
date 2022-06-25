package com.example.mppproject.Service;

import com.example.mppproject.Model.Address;
import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Repository.AddressRepository;
import com.example.mppproject.Repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AddressRepository addressRepository;

    public AppUserService(AppUserRepository appUserRepository, AddressRepository addressRepository) {
        this.appUserRepository = appUserRepository;
        this.addressRepository = addressRepository;
    }

    public List<AppUser> getAppUser() {
        return appUserRepository.findAll();
    }

    public void addAppUser(AppUser appUser) {
        Optional<AppUser> usernameEntry = appUserRepository.findByUserName(appUser.getUserName());
        if(usernameEntry.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }
        Address address = addressRepository.save(appUser.getAddress());
        appUser.setAddress(address);
        appUserRepository.save(appUser);

    }
}
