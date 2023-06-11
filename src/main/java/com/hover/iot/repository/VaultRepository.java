package com.hover.iot.repository;

import com.hover.iot.entity.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *  An interface that provides access to {@link Vault} data stored in a database.
 */
@Repository
public interface VaultRepository extends JpaRepository<Vault, Integer> {

    /**
     * Finds a vault by the key
     * @param key The key of the vault to find.
     * @return  An Optional containing the {@link Vault} object if found, else an empty Optional.
     */
    Optional<Vault> findVaultByKey(String key);

    void deleteVaultByKey(String key);
}
