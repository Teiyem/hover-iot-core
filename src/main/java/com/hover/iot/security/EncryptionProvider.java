package com.hover.iot.security;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * A utility class for encrypting and decrypting plaintext using the AES encryption algorithm with CBC mode and PKCS5Padding padding.
 */
public class EncryptionProvider {

    /**
     * The cipher algorithm used for encryption and decryption.
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    /**
     * The key algorithm used for encryption and decryption.
     */
    private static final String KEY_ALGORITHM = "AES";

    /**
     * Encrypts plaintext.
     *
     * @param plaintext The plaintext to encrypt.
     * @param key       The key used for encryption.
     * @return The Base64-encoded encrypted data.
     * @throws Exception if an error occurs during encryption.
     */
    public static @NotNull String encrypt(@NotNull String plaintext, @NotNull String key) throws Exception {
        byte[] iv = generateRandomIV();
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] plaintextBytes = plaintext.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] ciphertext = cipher.doFinal(plaintextBytes);

        byte[] encryptedBytes = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encryptedBytes, 0, iv.length);
        System.arraycopy(ciphertext, 0, encryptedBytes, iv.length, ciphertext.length);

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts encrypted data.
     *
     * @param encryptedText The encrypted data to decrypt.
     * @param key           The key to use to decrypt the data.
     * @return The decrypted plaintext.
     * @throws Exception if an error occurs during decryption.
     */
    public static @NotNull String decrypt(@NotNull String encryptedText, @NotNull String key) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] iv = new byte[16];
        byte[] ciphertext = new byte[encryptedBytes.length - iv.length];
        System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);
        System.arraycopy(encryptedBytes, iv.length, ciphertext, 0, ciphertext.length);

        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] plaintextBytes = cipher.doFinal(ciphertext);

        return new String(plaintextBytes, StandardCharsets.UTF_8);
    }

    /**
     * Generates a random initialization vector (IV) for encryption.
     *
     * @return a randomly generated initialization vector.
     */
    private static byte @NotNull [] generateRandomIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}

