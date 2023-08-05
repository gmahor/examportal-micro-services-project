package com.examportal.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@ConfigurationProperties
@Component
public class EnvConfiguration {

    @Value("${backendServerDetail}")
    private String backendServerDetail;

    @Value("${jwtSecret}")
    private String jwtSecret;

}
