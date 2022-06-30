package com.example.mppproject.Model.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.channels.GatheringByteChannel;
import java.util.Collection;
import java.util.List;

public class AuthenticationResponse {

    private String jwt;
    //@Autowired
    private MyUserDetails myUserDetailService;

    public AuthenticationResponse(String jwt, MyUserDetails myUserDetailService) {
        this.jwt = jwt;
        this.myUserDetailService = myUserDetailService;
    }

    public String getJwt() {
        return jwt;
    }

    public MyUserDetails getMyUserDetailService(){
        return myUserDetailService;
    }
}
