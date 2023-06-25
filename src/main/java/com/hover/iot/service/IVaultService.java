package com.hover.iot.service;

import com.hover.iot.entity.Vault;
import com.hover.iot.exception.VaultDataTransformationException;

/**
 * A service interface that defines the methods managing vault data.
 */
public interface IVaultService {

    /**
     * Adds a new vault.
     *
     * @param vault The vault to add.
     */
    void add(Vault vault);

    /**
     * Packs the provided data by encrypting it.
     *
     * @param arg The data to pack.
     * @param <T> The type of the data.
     * @return The packed data as a string.
     * @throws VaultDataTransformationException Thrown when an error occurs while transforming the data.
     */
    <T> String packData(T arg) throws VaultDataTransformationException;

    /**
     * Retrieves data from the vault with the specified UUID.
     *
     * @param key The key of the vault to retrieve data from.
     * @return The data from the vault.
     * @throws VaultDataTransformationException Thrown when an error occurs while transforming the data.
     */
    String getData(String key) throws VaultDataTransformationException;

    /**
     * Updates an existing vault.
     *
     * @param vault The vault to update.
     */
    void update(Vault vault);

    /**
     * Deletes the vault with the specified key.
     *
     * @param key The key of the vault to delete.
     */
    void delete(String key);
}
