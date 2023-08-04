package com.examportal.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {

    private final List<String> openApiEndpoints = Arrays.asList(
            "/api/v1/user/sign-up",
            "/api/v1/user/sign-in",
            "/api/v1/admin/admin-sign-in"
    );

    public Predicate<ServerHttpRequest> isSecured() {
        return request -> openApiEndpoints
                .stream()
                .noneMatch(uri -> request.getURI().getPath().contains(uri));
    }
}
