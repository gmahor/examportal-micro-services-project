package com.examportal.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnvConfiguration {

    @Value("${backendServerDetail}")
    private String backendServerDetail;

    @Value("${jwtSecret}")
    private String jwtSecret;


}
