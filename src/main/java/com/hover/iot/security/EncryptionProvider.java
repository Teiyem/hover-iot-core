package com.hover.iot.security;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
     * Encrypts the given plaintext using the given key and returns the encrypted text.
     *
     * @param plaintext the plaintext to be encrypted.
     * @param key       the key used for encryption.
     * @return the encrypted text.
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
     * Decrypts the given encrypted text using the given key and returns the plaintext.
     *
     * @param encryptedText the encrypted text to be decrypted.
     * @param key           the key used for decryption.
     * @return the decrypted plaintext.
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

