package com.alice_norris.inventoryproject.utils;

import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

public class LoginResultLauncher extends ActivityResultLauncher<Intent> {
    @Override
    public void launch(Intent input, @Nullable ActivityOptionsCompat options) {
        launch(input);
    }

    @Override
    public void unregister() {

    }

    @NonNull
    @Override
    public ActivityResultContract<Intent, ?> getContract() {
        return null;
    }
}
