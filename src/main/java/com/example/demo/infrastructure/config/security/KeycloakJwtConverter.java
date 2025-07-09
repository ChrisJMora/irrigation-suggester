package com.example.demo.infrastructure.config.security;

import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @Autowired
    private final SecurityProperties securityProperties;

    public KeycloakJwtConverter(
            SecurityProperties securityProperties
    ) {
        this.securityProperties = securityProperties;
    }

    private final Logger logger = LoggerFactory.getLogger(KeycloakJwtConverter.class);

    @Override
    public JwtAuthenticationToken convert(@NonNull Jwt jwt) {
        try {
            Set<GrantedAuthority> authorities = extractClientRoles(jwt);
            logger.debug("Extracted authorities for user '{}': {}",
                    jwt.getSubject(), authorities);
            return new JwtAuthenticationToken(jwt, authorities);
        } catch (Exception e) {
            logger.error("Failed to convert JWT to authentication token for subject: {}",
                    jwt.getSubject(), e);
            throw new JwtException("Invalid JWT token structure", e);
        }
    }

    private Set<GrantedAuthority> extractClientRoles(Jwt jwt) {
        String subject = jwt.getSubject();
        String issuer = jwt.getIssuer() != null ? jwt.getIssuer().toString() : "unknown";

        Map<String, Object> resourceAccess = jwt.getClaimAsMap(securityProperties.getKeycloak().resourceAccessClaim());

        if (isResourceAccessInvalid(resourceAccess)) {
            logger.warn("JWT validation failed - No resource_access for client '{}'. " +
                    "Subject: {}, Issuer: {}", securityProperties.getKeycloak().clientId(), subject, issuer);
            return Set.of();
        }

        Set<GrantedAuthority> authorities = extractRolesFromClientAccess(resourceAccess.get(securityProperties.getKeycloak().clientId()));

        logger.info("Successfully extracted {} authorities for user '{}' from issuer '{}'",
                authorities.size(), subject, issuer);

        return authorities;
    }

    private boolean isResourceAccessInvalid(Map<String, Object> resourceAccess) {
        return resourceAccess == null || !resourceAccess.containsKey(securityProperties.getKeycloak().clientId());
    }

    private Set<GrantedAuthority> extractRolesFromClientAccess(Object clientAccessObj) {
        if (!(clientAccessObj instanceof Map<?, ?> clientAccess)) {
            logger.warn("Client '{}' access configuration is not a valid map structure", securityProperties.getKeycloak().clientId());
            return Set.of();
        }

        Object rolesObj = clientAccess.get(securityProperties.getKeycloak().rolesClaim());
        if (!(rolesObj instanceof List<?> roles)) {
            logger.warn("No valid roles array found for client '{}' in JWT", securityProperties.getKeycloak().clientId());
            return Set.of();
        }

        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(this::addRolePrefix)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private String addRolePrefix(String role) {
        return securityProperties.getRolePrefix() + role;
    }
}