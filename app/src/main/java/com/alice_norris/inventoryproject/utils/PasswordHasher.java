package com.alice_norris.inventoryproject.utils;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

//This class is used to hash password for storage.
public class PasswordHasher {

    MessageDigest passwordHasher;
    public PasswordHasher(Context context){
        try {
            passwordHasher = MessageDigest.getInstance("SHA-256");
        } catch(GeneralSecurityException e) {
            Log.d("!!!SECURITY EXCEPTION!!!", e.getMessage());
        }
    }

    public String hashPassword(String password){
        byte[] bytePassword = password.getBytes(StandardCharsets.UTF_8);
        passwordHasher.update(bytePassword);
        return passwordHasher.digest().toString();
    }

}
