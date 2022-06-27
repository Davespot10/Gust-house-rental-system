package com.example.mppproject.Controller;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/AppUser")
public class AppUserController {
    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<AppUser> getAppUser() {
        return appUserService.getAppUser();
    }

    @PostMapping
    public List<AppUser> saveAppUser(@RequestBody AppUser appUser) {
        appUserService.addAppUser(appUser);
        return appUserService.getAppUser();
    }

    @PutMapping
    public List<AppUser> editAppUserAccount(@RequestBody AppUser appUser) throws RuntimeException {
        appUserService.editAppUserAccount(appUser);
        return appUserService.getAppUser();
    }




}
