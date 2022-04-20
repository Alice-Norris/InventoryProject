package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.ProductViewHolder;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class RemoveProductDialog extends DialogFragment {
    private String sku;
    private ProductViewModel fragmentProductViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        fragmentProductViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);

        View view = inflater.inflate(R.layout.remove_item_dialog, null);
        builder.setView(view)
                .setPositiveButton("Remove", (dialogInterface, i) -> {
                        TextInputEditText skuInput = view.findViewById(R.id.remove_item_sku_input);
                        sku = skuInput.getText().toString();
                        fragmentProductViewModel.removeProduct(sku);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        RemoveProductDialog.this.getDialog().cancel();
                });
        return builder.create();
    }
}