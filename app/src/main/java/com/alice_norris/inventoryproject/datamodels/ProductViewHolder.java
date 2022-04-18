package com.alice_norris.inventoryproject.datamodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alice_norris.inventoryproject.R;

public class ProductViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemQty;
        private final TextView itemName;
        private final TextView itemSku;
        private final Button deleteButton;
        private String skuToDelete;
        private ProductViewHolder(View view) {
            super(view);
            this.itemQty = view.findViewById(R.id.entryProductQty);
            this.itemName = view.findViewById(R.id.entryProductName);
            this.itemSku = view.findViewById(R.id.entryProductSku);
            this.deleteButton = view.findViewById(R.id.deleteButton);
        }

        public void bind(String quantity, String name, String sku) {
            itemQty.setText(quantity);
            itemName.setText(name);
            itemSku.setText(sku);
            skuToDelete = sku;
        }

        public String getSkuToDelete(){
            return skuToDelete;
        }

        public Button getDeleteButton(){
            return this.deleteButton;
        }

        public static ProductViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.inventory_item, parent, false);
            return new ProductViewHolder(view);
        }

}
