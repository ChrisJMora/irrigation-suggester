package com.example.demo.infrastructure.config.security;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
@Validated
public class SecurityProperties {

    @NotEmpty
    private String[] publicEndpoints;

    @NotEmpty
    private String rolePrefix;

    @NestedConfigurationProperty
    private Keycloak keycloak;

    @Validated
    public record Keycloak(
            @NotEmpty String clientId,
            @NotEmpty String resourceAccessClaim,
            @NotEmpty String rolesClaim
    ) {}
}