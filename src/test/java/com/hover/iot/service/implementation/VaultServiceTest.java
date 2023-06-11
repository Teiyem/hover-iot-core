package com.hover.iot.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hover.iot.entity.Vault;
import com.hover.iot.model.Credentials;
import com.hover.iot.repository.VaultRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VaultServiceTest {

    @Mock
    private VaultRepository vaultRepository;

    @InjectMocks
    private VaultService vaultService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAdd() throws JsonProcessingException {
        // Given
        var key = UUID.randomUUID().toString();
        var data = new Credentials("username:password", "test:password123");

        var vault = new Vault();
        vault.setId(0L);
        vault.setData(objectMapper.writeValueAsString(data));
        vault.setKey(key);

        // Then
        assertDoesNotThrow(() -> vaultService.add(vault));

        verify(vaultRepository, times(1)).save(vault);
    }

    @Test
    public void testUpdate() {
        // Given
        var key = "test-key";
        var newData = "new data";
        var existingVault = new Vault();
        existingVault.setKey(key);
        existingVault.setData("old data");

        // Mock
        when(vaultRepository.findVaultByKey(key)).thenReturn(Optional.of(existingVault));
        doAnswer(invocation -> {
            Vault updatedVault = invocation.getArgument(0);
            existingVault.setData(updatedVault.getData());
            return null;
        }).when(vaultRepository).save(existingVault);

        var updatedVault = new Vault();
        updatedVault.setKey(key);
        updatedVault.setData(newData);

        // When Then
        assertDoesNotThrow(() -> vaultService.update(updatedVault));

        assertEquals(newData, existingVault.getData());
        verify(vaultRepository, times(1)).findVaultByKey(key);
        verify(vaultRepository, times(1)).save(existingVault);
    }

    @Test
    public void testDelete() {
        // Given
        var key = "test-key";

        // Mock
        doNothing().when(vaultRepository).deleteVaultByKey(key);

        // When Then
        assertDoesNotThrow(() -> vaultService.delete(key));
        verify(vaultRepository, times(1)).deleteVaultByKey(key);
    }
}
