package com.accenture.assessment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiPathBuilder {
    @Value("${app.web-flux.base-path}")
    private String baseUrl;
    @Value("${app.web-flux.api-version}")
    private String apiVersion;

    public String buildPath(String requestPath) {
        return baseUrl + apiVersion + requestPath;
    }
}
