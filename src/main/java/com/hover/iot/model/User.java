package com.hover.iot.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User model class.
 */
@Entity
@Table(name = "tbl_user")
public class User implements UserDetails {
    /**
     * The user's id.
     */
    @Id
    @SequenceGenerator(
            name = "tbl_user_id_seq",
            sequenceName = "tbl_user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tbl_user_id_seq"
    )
    private Integer id;

    /**
     * The user's name.
     */
    private String name;

    /**
     * The user's username
     */
    @Column(unique = true)
    private String username;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's token.
     */
    @ElementCollection
    private List<String> tokens;

    /**
     * Initializes a new instance of {@link User} class. Default Constructor.
     */
    public User() {
    }

    /**
     * Initializes a new instance of {@link User} class with the given arguments.
     * @param name The user's name.
     * @param username The user's name.
     * @param password The user's password.
     * @param tokens The user's tokens.
     */
    public User(String name, String username, String password, List<String> tokens) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.tokens = tokens;
    }

    /**
     * Initializes a new instance of {@link User} class with the given arguments.
     * @param id The user's id.
     * @param name The user's name.
     * @param username The user's name.
     * @param password The user's password.
     * @param tokens The user's tokens.
     */
    public User(Integer id, String name, String username, String password, List<String> tokens) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.tokens = tokens;
    }

    /**
     * Gets the user's id.
     * @return The user's id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the user's name.
     * @return The user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the user's tokens.
     * @return The user's tokens.
     */
    public List<String> getTokens() {
        return tokens;
    }

    /**
     * Sets the user's tokens.
     * @param tokens The user's tokens to set.
     */
    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

    /**
     * Gets a list of all the roles that the user has.
     *
     * @return A list of SimpleGrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Gets the user's password of the user.
     *
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * It returns the username of the user.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Gets of if the account is not expired.
     * @return true.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Gets of if the account is not locked.
     * @return true.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Gets if the user is not expired.
     * @return true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Gets if the user is enabled
     * @return true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
