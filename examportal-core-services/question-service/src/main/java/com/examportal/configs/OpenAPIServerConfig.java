package com.examportal.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.oas.web.OpenApiTransformationContext;
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter;
import springfox.documentation.spi.DocumentationType;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Component
public class OpenAPIServerConfig implements WebMvcOpenApiTransformationFilter {

    private final EnvConfiguration envConfiguration;

    @Autowired
    public OpenAPIServerConfig(EnvConfiguration envConfiguration) {
        this.envConfiguration = envConfiguration;
    }

    @Override
    public OpenAPI transform(OpenApiTransformationContext<HttpServletRequest> context) {
        Server server = new Server();
        server.setDescription("Base Url");
        server.setUrl(envConfiguration.getBackendServerDetail());
        OpenAPI openAPI = context.getSpecification();
        openAPI.setServers(Collections.singletonList(server));
        return null;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return documentationType.equals(DocumentationType.OAS_30);
    }
}
