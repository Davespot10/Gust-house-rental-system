package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Enum.RoleType;
import com.example.mppproject.Model.HomeProperty;
import com.example.mppproject.Model.Role;
import com.example.mppproject.Repository.HomePropertyRepository;
import com.example.mppproject.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.example.mppproject.Model.Enum.RoleType.*;
@Component
public class RoleSeed implements CommandLineRunner {
    private final RoleRepository roleRepository;
    public RoleSeed(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        loadData();
    }
    private void loadData() {
        if (roleRepository.count() == 0) {
            Role data1 = new Role(ADMIN);
            Role data2 = new Role(GUEST);
            Role data3 = new Role(HOST);

            roleRepository.save(data1);
            roleRepository.save(data2);
            roleRepository.save(data3);

        }
    }
}

