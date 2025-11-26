package com.calculadora.de_gastos.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;


import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;

import com.calculadora.de_gastos.ui.home.HomeActivity;
import com.calculadora.de_gastos.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class AuthCheckActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SharedPreferences userPrefs;
    private static final String PREFS_NAME = "_UserProfilePrefs";
    private static final String KEY_USER_NAME = "user_name";

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(AuthCheckActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                showHome();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verificación de Seguridad")
                .setSubtitle("Autenticación con huella digital o bloqueo de pantalla para continuar.")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            checkBiometricsAndAuthenticate();
        } else {
            showLogin();
        }
    }

    private void checkBiometricsAndAuthenticate() {
        BiometricManager biometricManager = BiometricManager.from(this);

        int canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS || canAuthenticate == BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED) {
            biometricPrompt.authenticate(promptInfo);
        } else {
            showHome();
        }
    }

    private void showHome() {
        Intent intent = new Intent(AuthCheckActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLogin() {
        Intent intent = new Intent(AuthCheckActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}