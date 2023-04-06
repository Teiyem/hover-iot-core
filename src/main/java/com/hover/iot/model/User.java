package com.hover.iot.model;

import com.hover.iot.enumeration.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * User model class.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * The user's roles.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * This function returns a list of all the roles that the user has.
     *
     * @return A list of SimpleGrantedAuthority objects.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    /**
     * It returns the password of the user.
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
     * This function returns true if the account is not expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * This function returns true if the account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * If the user is not expired, then return true.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * If the user is enabled, return true.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
