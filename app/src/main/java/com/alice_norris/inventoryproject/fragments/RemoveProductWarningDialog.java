package com.alice_norris.inventoryproject.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.databinding.ActivityMainBinding;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.alice_norris.inventoryproject.databinding.RemoveItemWarningBinding;
public class RemoveProductWarningDialog extends DialogFragment {

    private ProductViewModel dialogProductViewModel;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        dialogProductViewModel = new ViewModelProvider(requireActivity())
                .get(ProductViewModel.class);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.remove_item_warning, null);
        RemoveItemWarningBinding binding = RemoveItemWarningBinding.inflate(inflater);
        binding.setProductViewModel(dialogProductViewModel);
        binding.setLifecycleOwner(getActivity());
        builder.setView(binding.getRoot())
                .setPositiveButton("Remove", (dialogInterface, i) -> {
                    dialogProductViewModel.removeProduct(dialogProductViewModel.requestedProduct2.productSku);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                    this.getDialog().cancel();
                });
        return builder.create();
    }
}
