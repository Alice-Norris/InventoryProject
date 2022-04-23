package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Update;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateProductDialog extends DialogFragment {
    private Product product;
    ProductViewModel fragmentProductViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        assert getActivity() != null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_item_dialog, null);
        fragmentProductViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        TextView header = view.findViewById(R.id.update_item_dialog_header);
        TextView currentNameLabel = view.findViewById(R.id.update_item_current_name);
        TextView currentQtyLabel = view.findViewById(R.id.update_item_current_qty);
        header.setText(getString(R.string.update_item_header, product.productSku));
        currentNameLabel.setText(
                getString(R.string.update_item_current_name, product.productName));
        currentQtyLabel.setText(
                getString(R.string.update_item_current_qty, product.productQuantity));
        TextInputEditText nameInput = view.findViewById(R.id.update_item_name_input);
        TextInputEditText qtyInput = view.findViewById(R.id.update_item_qty_input);
        builder.setView(view).setPositiveButton("Update", (dialogInterface, i) ->{
            if (nameInput.getText().length() != 0){
                product.productName = nameInput.getText().toString();
            }
            if (nameInput.getText().length() !=0){
                product.productQuantity = qtyInput.getText().toString();
            }
            fragmentProductViewModel.updateProduct(product);
        })
                .setNegativeButton("Cancel",
                        (dialogInterface, i) -> UpdateProductDialog.this.getDialog().cancel());

        return builder.create();
    }
}
