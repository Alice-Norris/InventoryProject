package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.alice_norris.inventoryproject.R;
import com.google.android.material.textfield.TextInputEditText;

public class RemoveProductDialog extends DialogFragment {
    private String sku;
    private RemoveItemDialogListener listener;

    public interface RemoveItemDialogListener {
        public void onRemovePositiveClick(String sku);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remove_item_dialog, null);
        builder.setView(view)
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText skuInput = view.findViewById(R.id.remove_item_sku_input);
                        sku = skuInput.getText().toString();
                        listener.onRemovePositiveClick(sku);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RemoveProductDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (RemoveItemDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("Activity doesn't implement listener!");
        }
    }
}