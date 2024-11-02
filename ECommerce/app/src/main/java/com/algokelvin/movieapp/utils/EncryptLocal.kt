package com.algokelvin.movieapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object EncryptLocal {
    fun saveToken(context: Context, token: String) {
        val sharedPreferences = getEncryptedPrefs(context)
        with(sharedPreferences.edit()) {
            putString("user_token", token)
            apply()
        }
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = getEncryptedPrefs(context)
        return sharedPreferences.getString("user_token", null)
    }

    private fun getEncryptedPrefs(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        return EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}