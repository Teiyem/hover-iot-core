package com.hover.iot.entity;

import jakarta.persistence.*;

/**
 * An entity class that represents a data vault.
 */
@Entity
@Table(name = "TBL_VAULT")
public class Vault {
    /**
     * The vault's id.
     */
    @Id
    @SequenceGenerator(
            name = "tbl_vault_id_seq",
            sequenceName = "tbl_vault_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "tbl_vault_id_seq"
    )
    private Long id;

    /**
     * The vault's data key.
     */
    private String key;

    /**
     * The vault's data.
     */
    private String data;

    /**
     * Initializes a new instance of {@link Vault} class.
     */
    public Vault() {
    }

    /**
     * Gets the vault's id.
     *
     * @return The vault's id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the vault's id.
     *
     * @param id The vault id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the vault's key.
     *
     * @return The vault's key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the vault's key.
     *
     * @param key The vault key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the vault's data.
     *
     * @return The vault's data.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the vault's data.
     *
     * @param data The vault data to set.
     */
    public void setData(String data) {
        this.data = data;
    }
}
