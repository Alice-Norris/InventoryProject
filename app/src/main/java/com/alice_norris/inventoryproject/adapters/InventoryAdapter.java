package com.alice_norris.inventoryproject.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewHolder;
import com.alice_norris.inventoryproject.utils.AdapterEventListener;

public class InventoryAdapter extends ListAdapter<Product, ProductViewHolder> {
    private final AdapterEventListener adapterEventListener;

    public InventoryAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback,
                            AdapterEventListener listener){
        super(diffCallback);
        adapterEventListener = listener;

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return ProductViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = getItem(position);
        holder.bind(currentProduct.productQuantity,
                currentProduct.productName,
                currentProduct.productSku,
                adapterEventListener);
    }

    public static class ProductDiff extends DiffUtil.ItemCallback<Product>{
        @Override
        public boolean areItemsTheSame(@NonNull Product oldProduct, @NonNull Product newProduct) {
            return oldProduct == newProduct;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.productSku.equals(newItem.productSku);
        }
    }
}
