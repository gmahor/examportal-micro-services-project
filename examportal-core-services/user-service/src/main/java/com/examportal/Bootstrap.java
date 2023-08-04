package com.examportal;

import com.examportal.services.AdminService;
import com.examportal.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserService userService;

    private final AdminService adminService;

    @Autowired
    public Bootstrap(UserService userService,
                     AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
//            this.adminService.createAdmin();
        } catch (Exception e) {
            log.error("Exception In On Application Event Service - ", e);
        }
    }
}
