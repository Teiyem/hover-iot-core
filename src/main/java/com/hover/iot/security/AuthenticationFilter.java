package com.hover.iot.security;

import com.hover.iot.enumeration.TokenType;
import com.hover.iot.service.ITokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * If the request has a valid access token, then set the authentication context to the user associated
 * with the token
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    /**
     * A service that is used to load the user details from the database.
     */
    private final UserDetailsService userDetailsService;

    /**
     * A service that is used to create and verify access and refresh tokens.
     */
    private final ITokenService tokenService;

    /**
     * Initializes a new instance of {@link AuthenticationFilter} class with the given arguments.
     * @param userDetailsService The user details service used to load user-specific data.
     * @param tokenService The token service used to generate and validate tokens.
     */
    public AuthenticationFilter(UserDetailsService userDetailsService, ITokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    /**
     * If the request has a valid access token, then set the authentication context to the user associated
     * with the token
     * @param request     The request object
     * @param response    The response object that is passed to the filter.
     * @param filterChain The filter chain that the request will be passed through.
     */
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        final String username = tokenService.extractUsername(token, TokenType.ACCESS);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails user = this.userDetailsService.loadUserByUsername(username);

            if (tokenService.isTokenValid(token, user, TokenType.ACCESS)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                        null, user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
