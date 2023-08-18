package com.examportal;

import com.examportal.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleService roleService;

    @Autowired
    public Bootstrap(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            roleService.saveRoleIfNotExists();
        } catch (Exception e) {
            log.error("Exception In On Application Event Service - ", e);
        }
    }
}
