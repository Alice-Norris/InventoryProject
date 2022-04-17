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

import org.w3c.dom.Text;

public class AddProductDialog extends DialogFragment {
    private String sku;
    private String name;
    private String qty;

    public interface AddItemDialogListener {
        public void onDialogPositiveClick(String sku, String name, String qty);
    }

    AddItemDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_item_dialog, null);
        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TextInputEditText skuInput = view.findViewById(R.id.add_item_sku_input);
                        TextInputEditText nameInput = view.findViewById(R.id.add_item_name_input);
                        TextInputEditText qtyInput = view.findViewById(R.id.add_item_qty_input);

                        sku = skuInput.getText().toString();
                        name = nameInput.getText().toString();
                        qty = qtyInput.getText().toString();

                        listener.onDialogPositiveClick(sku, name, qty);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AddProductDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (AddItemDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException("Activity doesn't implement listener!");
        }
    }
}
