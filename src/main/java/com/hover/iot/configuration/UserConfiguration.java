package com.hover.iot.configuration;

import com.hover.iot.model.User;
import com.hover.iot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Defines and configures beans related to user management and authentication,
 * such as an authentication provider, user details service, and password encoder.
 */
@Configuration
@RequiredArgsConstructor
public class UserConfiguration {

    /**
     * A repository that provides CRUD operations for the {@link User} class.
     */
    private final UserRepository userRepository;

    /**
     * Returns an implementation of the UserDetailsService interface, which is responsible for
     * retrieving user details from a data source.
     *
     * @return an implementation of UserDetailsService.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Returns an instance of the AuthenticationProvider interface, which is responsible for authenticating users
     * based on their credentials.
     *
     * @return An instance of AuthenticationProvider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Returns an instance of the AuthenticationManager interface, which is responsible for authenticating users
     * and managing their credentials.
     *
     * @param config An instance of the AuthenticationConfiguration class that contains the necessary
     *               configuration for the authentication manager.
     * @return An instance of AuthenticationManager.
     * @throws Exception If there is an error creating the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Returns an instance of the BCryptPasswordEncoder class, which is used for encoding
     * passwords with the bcrypt hashing algorithm.
     *
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
