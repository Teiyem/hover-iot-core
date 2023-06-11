package com.hover.iot.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.exception.EntityNotFoundException;
import com.hover.iot.exception.ResourceConflictException;
import com.hover.iot.entity.Vault;
import com.hover.iot.repository.VaultRepository;
import com.hover.iot.security.EncryptionProvider;
import com.hover.iot.service.IVaultService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * A service class that handles operations related to vault data management. Implements the {@link IVaultService} interface.
 */
@Service
public class VaultService implements IVaultService {

    /**
     * The encryption and decryption key.
     */
    @Value("vault.service.enc.dec.key")
    private String vaultKey;

    /**
     * The vault repository.
     */
    private final VaultRepository vaultRepository;

    /**
     * Initializes a new instance of {@link DeviceService} class.
     *
     * @param vaultRepository The vault repository.
     */
    public VaultService(VaultRepository vaultRepository) {
        this.vaultRepository = vaultRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Vault vault) {
        try {
            vaultRepository.save(vault);
        } catch (Exception exception) {
            throw new ResourceConflictException("Vault operation error");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> String packData(T arg) {
        try {
            var data = new ObjectMapper().writeValueAsString(arg);
            return EncryptionProvider.encrypt(data, vaultKey);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getData(String key) {
        var vault = vaultRepository.findVaultByKey(key)
                .orElseThrow(() -> new EntityNotFoundException("Failed to get vault data"));

        try {
            return EncryptionProvider.decrypt(vault.getData(), vaultKey);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(@NotNull Vault vault) {
        var oldVault = vaultRepository.findVaultByKey(vault.getKey())
                .orElseThrow(() -> new EntityNotFoundException("Failed to get vault data"));

        oldVault.setData(vault.getData());

        vaultRepository.save(oldVault);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String key) {
        vaultRepository.deleteVaultByKey(key);
    }
}
