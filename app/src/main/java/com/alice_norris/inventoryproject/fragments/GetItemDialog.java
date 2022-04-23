package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Update;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;

public class GetItemDialog extends DialogFragment {
    ProductViewModel fragmentProductViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        fragmentProductViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        View view = inflater.inflate(R.layout.get_item_dialog, null);
        builder.setView(view).setPositiveButton("Add", (dialogInterface, i) ->{
            TextView getItemSkuInput = view.findViewById(R.id.get_item_sku_input);
            String itemSku = getItemSkuInput.getText().toString();
            fragmentProductViewModel.getProductBySku(itemSku);
            DialogFragment updateProductDialog = new UpdateProductDialog();
            updateProductDialog.show(getParentFragmentManager(), "Update Item");
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
            this.getDialog().cancel();
        });

        return builder.create();
    }
}
