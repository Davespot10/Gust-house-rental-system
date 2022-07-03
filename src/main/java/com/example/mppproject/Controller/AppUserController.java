package com.example.mppproject.Controller;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Service.AppUserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/AppUser")
public class AppUserController {
    @Autowired
    private  AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }


    @GetMapping
    public List<AppUser> getAppUser() {
        return appUserService.getAppUser();
    }

    @GetMapping(path = "{id}")
    public AppUser getAppUserById(@PathVariable("id") long id) {
        return appUserService.getAppUserById(id);
    }

    @PostMapping
    public AppUser saveAppUser(@RequestBody AppUser appUser) {

        return appUserService.addAppUser(appUser);
    }

    @PostMapping("/addAmount")
    public List<AppUser> editAppUserAccount(@RequestBody AppUser appUser) throws RuntimeException {
        appUserService.editAppUserAccount(appUser);
        return appUserService.getAppUser();
    }




}
