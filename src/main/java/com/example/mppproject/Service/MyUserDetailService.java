package com.example.mppproject.Service;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.authentication.MyUserDetails;
import com.example.mppproject.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> user = appUserRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: " + username));
        return user.map(MyUserDetails::new).get();
    }
}
