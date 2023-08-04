package com.examportal.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@ConfigurationProperties
@Component
public class EnvConfiguration {

    @Value("${backendServerDetail}")
    private String backendServerDetail;

    @Value("${jwtKeyPath}")
    private String jwtKeyPath;

    @Value("${accessTokenExpiryTime}")
    private long accessTokenExpiryTime;

    @Value("${jwtSecret}")
    private String jwtSecret;

}
