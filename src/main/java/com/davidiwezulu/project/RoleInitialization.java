package com.davidiwezulu.project;

import com.davidiwezulu.project.model.Role;
import com.davidiwezulu.project.model.RoleName;
import com.davidiwezulu.project.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RoleInitialization {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                Role userRole = new Role();
                userRole.setId(1L);
                userRole.setName(RoleName.ROLE_USER);
                roleRepository.save(userRole);

                Role adminRole = new Role();
                adminRole.setId(2L);
                adminRole.setName(RoleName.ROLE_ADMIN);
                roleRepository.save(adminRole);

                Role moderatorRole = new Role();
                moderatorRole.setId(3L);
                moderatorRole.setName(RoleName.ROLE_MODERATOR);
                roleRepository.save(moderatorRole);
            }
        };
    }
}

