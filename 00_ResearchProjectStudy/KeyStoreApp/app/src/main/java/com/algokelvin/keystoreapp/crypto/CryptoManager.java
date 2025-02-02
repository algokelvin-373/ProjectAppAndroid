package com.algokelvin.keystoreapp.crypto;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptoManager {

    private static final String ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final String TRANSFORMATION = ALGORITHM + "/" + BLOCK_MODE + "/" + PADDING;
    private final String alias;

    private final KeyStore keyStore;

    public CryptoManager() throws Exception {
        keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        alias = "Algo_Alias_"+ System.currentTimeMillis();
    }

    private Cipher getEncryptCipher() throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, getKey());
        return cipher;
    }

    private Cipher getDecryptCipherForIv(byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, getKey(), new IvParameterSpec(iv));
        return cipher;
    }

    private SecretKey getKey() throws Exception {
        KeyStore.SecretKeyEntry existingKey = (KeyStore.SecretKeyEntry) keyStore.getEntry("secret", null);
        if (existingKey != null) {
            return existingKey.getSecretKey();
        } else {
            return createKey();
        }
    }

    private SecretKey createKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(
                new KeyGenParameterSpec
                        .Builder("secret", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setUserAuthenticationRequired(false)
                        .setRandomizedEncryptionRequired(true)
                        .build()
        );
        return keyGenerator.generateKey();
    }

    public byte[] encrypt(byte[] bytes) throws Exception {
        Cipher encryptCipher = getEncryptCipher();
        return encryptCipher.doFinal(bytes);
    }

    public byte[] encrypt(byte[] bytes, OutputStream outputStream) throws Exception {
        Cipher encryptCipher = getEncryptCipher();
        byte[] encryptedBytes = encryptCipher.doFinal(bytes);
        outputStream.write(encryptCipher.getIV().length);
        outputStream.write(encryptCipher.getIV());
        outputStream.write(encryptedBytes.length);
        outputStream.write(encryptedBytes);
        return encryptedBytes;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public byte[] decrypt(String encryptString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptString); // Decode the input string from Base64 to get the raw encrypted bytes

        // Extract the IV size (assuming it's always a fixed size, e.g., 16 bytes for AES)
        // Assuming a fixed IV size of 16 bytes for AES
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        System.arraycopy(decodedBytes, 0, iv, 0, ivSize); // Copy the IV from the decoded bytes

        // Extract the encrypted data
        int encryptedDataStartIndex = ivSize;
        int encryptedDataSize = decodedBytes.length - encryptedDataStartIndex;
        byte[] encryptedBytes = new byte[encryptedDataSize];
        System.arraycopy(decodedBytes, encryptedDataStartIndex, encryptedBytes, 0, encryptedDataSize);
        System.out.println("IV: " + java.util.Arrays.toString(iv));
        System.out.println("Encrypted Bytes: " + java.util.Arrays.toString(encryptedBytes));

        // Initialize the Cipher for decryption with the extracted IV
        Cipher decryptCipher;
        try {
            decryptCipher = getDecryptCipherForIv(iv);
            byte[] decryptedBytes = decryptCipher.doFinal(encryptedBytes);
            System.out.println("Decrypted Bytes: " + java.util.Arrays.toString(decryptedBytes));
            return decryptedBytes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(InputStream inputStream) throws Exception {
        int ivSize = inputStream.read();
        byte[] iv = new byte[ivSize];
        inputStream.read(iv);

        int encryptedBytesSize = inputStream.read();
        byte[] encryptedBytes = new byte[encryptedBytesSize];
        inputStream.read(encryptedBytes);

        Cipher decryptCipher = getDecryptCipherForIv(iv);
        return decryptCipher.doFinal(encryptedBytes);
    }
}
