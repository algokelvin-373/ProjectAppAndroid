package com.algokelvin.keystoreapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.algokelvin.keystoreapp.crypto.CryptoManager;
import com.algokelvin.keystoreapp.crypto.CryptoManagerTrain;
import com.algokelvin.keystoreapp.databinding.ActivityMainBinding;

import java.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    //private CryptoManager cryptoManager;
    private CryptoManagerTrain cryptoManagerTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        try {
            cryptoManagerTrain = new CryptoManagerTrain();
            initialize(binding);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initialize(ActivityMainBinding binding) {
        binding.btnEncrypt.setOnClickListener(v -> {
            String message = binding.edtMessage.getText().toString().trim();
            encryptWithKeyStore(message);
        });

        binding.btnDecrypt.setOnClickListener(v -> {
            String strEncrypt = binding.txtResultEncryptDecrypt.getText().toString().trim();
            binding.edtMessage.setText(strEncrypt);
            try {
                decryptWithKeyStore2(strEncrypt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // Encrypted data is now in the `encryptedData` array, and you can use it as needed.
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void encryptWithKeyStore(String message) {
        byte[] encryptedData;
        try {
            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            encryptedData = cryptoManagerTrain.encrypt(message.getBytes());
            String strBase64 = java.util.Base64.getEncoder().encodeToString(encryptedData);
            binding.txtResultEncryptDecrypt.setText(strBase64);
        } catch (Exception e) { // Handle encryption error
            e.printStackTrace();
        }
    }

    private void decryptWithKeyStore2(String strEncrypt) throws Exception {
        byte[] encryptByte = Base64.getDecoder().decode(strEncrypt);
        byte[] decryptedData = cryptoManagerTrain.decrypt(encryptByte);
        String decryptedMessage = new String(decryptedData);
        binding.txtResultEncryptDecrypt.setText(decryptedMessage);
        /*try {
            byte[] decryptedData = cryptoManagerTrain.decrypt(encryptedDataWithIv);
            String decryptedMessage = new String(decryptedData);
            // Use the decrypted message
        } catch (Exception e) { // Handle decryption error
            e.printStackTrace();
        }*/
    }

    private void decryptWithKeyStore(String strEncrypt) {
//        byte[] decryptData;
//        decryptData = cryptoManagerTrain.decrypt(strEncrypt);
//        String resultDecrypt = new String(decryptData);
//        binding.txtResultEncryptDecrypt.setText(resultDecrypt);
    }
}