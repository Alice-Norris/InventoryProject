package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
public class RemoveProductWarningDialog extends DialogFragment {

    private TextView header;
    private ProductViewModel dialogProductViewModel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        String sku = getArguments().getString("sku");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remove_item_warning, null);
        builder.setView(view)
                .setPositiveButton("Remove", (dialogInterface, i) -> {
                    dialogProductViewModel.removeProduct(sku);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    this.getDialog().cancel();
                });
        TextView skuLabel = view.findViewById(R.id.remove_item_warning_sku);
        TextView nameLabel = view.findViewById(R.id.remove_item_warning_name);
        dialogProductViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        Product product = dialogProductViewModel.getProductBySku(sku);
        skuLabel.setText(getString(R.string.remove_item_warning_sku, product.productSku));
        nameLabel.setText(getString(R.string.remove_item_warning_name, product.productName));
        return builder.create();
    }
}
