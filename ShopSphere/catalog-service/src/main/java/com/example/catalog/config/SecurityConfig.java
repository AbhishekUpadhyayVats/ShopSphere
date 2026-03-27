package com.example.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(c -> c.disable());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(req -> req
				
				//FOR SWAGGER-UI
				.requestMatchers("/swagger-ui/**", "/v3/api-docs","/swagger-ui.html").permitAll()

				// ADMIN ONLY -> PUTTING IT FIRST
				.requestMatchers(HttpMethod.GET, "/product/count").hasRole("ADMIN")

				// INTERNAL (Order Service)
				.requestMatchers(HttpMethod.PUT, "/product/reduceStock/*", "/product/increaseStock/*").authenticated()

				.requestMatchers(HttpMethod.GET, "/actuator/**").authenticated()

				// PUBLIC APIs
				.requestMatchers(HttpMethod.GET, "/product", "/product/gettingProduct/**", "/category/**").permitAll()

				// ALL OTHER APIs → ADMIN
				.anyRequest().hasRole("ADMIN"));

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}