package com.example.mppproject.Model.authentication;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OneToMany;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public class MyUserDetails implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private boolean active;

    private List<GrantedAuthority> roles;

    public MyUserDetails(AppUser user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.active = true;
        StringBuilder role = new StringBuilder();
        for (Role r : user.getRoles()){
            role.append(r.getRoleType()+",");
        }
        //user.getRoles().stream().forEachOrdered((ur)->roles.add((ur.getRoleType()));
        String roles = role.toString();
        this.roles = Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Long getId(){
        return id;
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
