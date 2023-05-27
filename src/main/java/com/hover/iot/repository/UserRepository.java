package com.hover.iot.repository;

import com.hover.iot.model.User;
import com.hover.iot.model.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 *  An interface that provides access to {@link User} data stored in a database.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their username.
     * @param username The username to find the user by.
     * @return  An Optional containing the {@link User} object if found, else an empty Optional.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by a token in their list of tokens.
     * @param token The token to find the user by.
     * @return  An Optional containing the {@link User} object if found, else an empty Optional.
     */
    Optional<User> findByTokensContaining(String token);
}
