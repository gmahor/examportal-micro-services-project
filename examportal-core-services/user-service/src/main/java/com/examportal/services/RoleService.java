package com.examportal.services;

import com.examportal.entities.Role;
import com.examportal.enums.RoleType;
import com.examportal.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void saveRoleIfNotExists() {
        if (roleRepository.count() == 0) {
            List<Role> roles = new ArrayList<>();
            Role userRole = new Role(RoleType.USER);
            Role adminRole = new Role(RoleType.ADMIN);
            roles.add(userRole);
            roles.add(adminRole);
            roleRepository.saveAllAndFlush(roles);
            log.info("All role are added successfully in Database");
        }
    }
}