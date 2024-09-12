package com.algokelvin.keystoreapp.crypto;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class CryptoManagerTrain {

    private static final String ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    private static final String BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC;
    private static final String PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7;
    private static final String TRANSFORMATION = ALGORITHM + "/" + BLOCK_MODE + "/" + PADDING;

    private final KeyStore keyStore;

    public CryptoManagerTrain() throws Exception {
        keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        //alias = "Algo_Alias_"+ System.currentTimeMillis();
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
        byte[] iv = encryptCipher.getIV(); // Dapatkan IV dari cipher
        byte[] encryptedData = encryptCipher.doFinal(bytes);

        // Gabungkan IV dan encryptedData, atau simpan IV di tempat terpisah
        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

        return combined;
    }

    public byte[] decrypt(byte[] encryptedDataWithIv) throws Exception {
        byte[] iv = new byte[16]; // Panjang IV untuk AES adalah 16 byte
        byte[] encryptedData = new byte[encryptedDataWithIv.length - iv.length];

        // Pisahkan IV dan encryptedData
        System.arraycopy(encryptedDataWithIv, 0, iv, 0, iv.length);
        System.arraycopy(encryptedDataWithIv, iv.length, encryptedData, 0, encryptedData.length);

        Cipher decryptCipher = getDecryptCipherForIv(iv);
        return decryptCipher.doFinal(encryptedData);
    }
}
