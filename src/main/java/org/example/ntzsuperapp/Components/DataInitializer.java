package org.example.ntzsuperapp.Components;

import org.example.ntzsuperapp.Entity.Role;
import org.example.ntzsuperapp.Entity.RoleName;
import org.example.ntzsuperapp.Repo.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    public DataInitializer(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public void run(String... args) {
        if (roleRepo.findByName(RoleName.ROLE_USER).isEmpty()) {
            roleRepo.save(new Role(RoleName.ROLE_USER));
        }
        if (roleRepo.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
            roleRepo.save(new Role(RoleName.ROLE_ADMIN));
        }
    }
}
