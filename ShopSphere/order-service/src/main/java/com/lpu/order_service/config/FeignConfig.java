package com.lpu.order_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {

            var auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {

                //Username
                String username = auth.getName();

                //Role (remove ROLE_ prefix before sending)
                String role = auth.getAuthorities()
                        .stream()
                        .findFirst()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .orElse(null);

                //Token (already stored as "Bearer xxx")
                String token = auth.getCredentials() != null
                        ? auth.getCredentials().toString()
                        : null;

                //Forward headers

                if (token != null) {
                    template.header("Authorization", token);
                }

                if (username != null) {
                    template.header("X-User", username);
                }

                if (role != null) {
                    template.header("X-Role", role);
                }
            }
        };
    }
}