package com.hover.iot.configuration;

import com.hover.iot.response.ApiResponse;
import com.hover.iot.security.AuthenticationFilter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configures and customizes Spring Security for a web application, such as specifying security filters,
 * authentication providers, access control rules, and other security-related settings.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * An array of URL endpoints that are publicly accessible without authentication.
     */
    private final String[] OPEN_URLS = {
            "/api/v1/user/register",
            "/api/v1/user/login"
    };

    /**
     * A custom filter that is used to authenticate the user.
     */
    private final AuthenticationFilter authenticationFilter;

    /**
     * A bean that is used to authenticate the user.
     */
    private final AuthenticationProvider authenticationProvider;

    /**
     * Initializes a new instance of {@link ApiResponse} class with the given arguments.
     * @param authenticationFilter The authentication filter used to handle incoming authentication requests.
     * @param authenticationProvider The authentication provider used to verify user credentials.
     */
    public SecurityConfiguration(AuthenticationFilter authenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * Returns a Spring Security filter chain.
     *
     * @param http The HttpSecurity object that is used to configure the security filter chain
     * @return A {@link SecurityFilterChain} object that represents the configured security filter chain.
     * @throws Exception if there is an error while building the security filter chain.
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(OPEN_URLS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
