package com.alice_norris.inventoryproject.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstaller.SessionParams;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.activities.MainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class AllowNotificationDialog extends DialogFragment {
    private NotificationDialogListener listener;

    public interface NotificationDialogListener{
        public void onNotificationDialogAllow(DialogFragment dialog);
        public void onNotificationDialogDeny(DialogFragment dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.allow_notification_dialog, null);
        builder.setView(view).setPositiveButton( "Allow", (dialogInterface, i) -> {
            listener.onNotificationDialogAllow(this);
        }).setNegativeButton("Deny", (dialogInterface, i) ->{
            listener.onNotificationDialogDeny(this);
            this.getDialog().cancel();
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (NotificationDialogListener) context;
    }
}
