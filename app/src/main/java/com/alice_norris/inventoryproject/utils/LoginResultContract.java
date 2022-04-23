package com.alice_norris.inventoryproject.utils;

import static android.app.Activity.RESULT_OK;


import android.content.Context;
import android.content.Intent;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alice_norris.inventoryproject.activities.LoginActivity;

public class LoginResultContract extends ActivityResultContract<Void, String> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Void input) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public String parseResult(int resultCode, @Nullable Intent intent) {
        if(resultCode == RESULT_OK && intent != null){
            return intent.getStringExtra("userFirstName");
        } else {
            return null;
        }
    }
}
