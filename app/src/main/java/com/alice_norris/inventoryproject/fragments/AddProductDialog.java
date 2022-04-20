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
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddProductDialog extends DialogFragment {
    private String sku;
    private String name;
    private String qty;
    ProductViewModel fragmentProductViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        fragmentProductViewModel = new ViewModelProvider(getActivity())
                .get(ProductViewModel.class);
        View view = inflater.inflate(R.layout.add_item_dialog, null);
        builder.setView(view).setPositiveButton("Add", (dialogInterface, i) ->{
                        TextInputEditText skuInput = view.findViewById(R.id.add_item_sku_input);
                        TextInputEditText nameInput = view.findViewById(R.id.add_item_name_input);
                        TextInputEditText qtyInput = view.findViewById(R.id.add_item_qty_input);

                        sku = skuInput.getText().toString();
                        name = nameInput.getText().toString();
                        qty = qtyInput.getText().toString();
                        fragmentProductViewModel.addProduct(sku, name, qty);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    AddProductDialog.this.getDialog().cancel();
                });

        return builder.create();
    }

}
