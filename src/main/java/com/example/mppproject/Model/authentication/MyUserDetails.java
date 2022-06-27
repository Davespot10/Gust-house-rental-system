package com.example.mppproject.Model.authentication;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> roles;

    public MyUserDetails(AppUser user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = true;
        for (Role r : user.getRoles()){
            this.roles.add((GrantedAuthority) r);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
