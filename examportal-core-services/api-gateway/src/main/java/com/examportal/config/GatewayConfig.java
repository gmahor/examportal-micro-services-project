package com.examportal.config;

import com.examportal.filters.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service-route", r -> r.path("/api/v1/user/**", "/api/v1/admin/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                .route("quiz-service-route", r -> r.path("/api/v1/quiz/**", "/api/v1/cat/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://quiz-service"))
                .route("question-service-route", r -> r.path("/api/v1/question/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://question-service"))
                .build();
    }
}
