package com.algokelvin.biometric;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import com.algokelvin.biometric.utils.BiometricFunction;

public class MainActivity extends AppCompatActivity {
    private final BiometricFunction biometricFunction = new BiometricFunction(this);
    private BiometricPrompt biometricPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button biometricLoginButton = findViewById(R.id.biometric_login);
        setBiometricPrompt();
        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(biometricFunction.getPromptInfo());
        });

    }

    private void setBiometricPrompt() {
        biometricPrompt = new BiometricPrompt(this, biometricFunction.getExecutor(), new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}