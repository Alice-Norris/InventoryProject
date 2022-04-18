package com.alice_norris.inventoryproject.adapters;

import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewHolder;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;

import java.util.List;

public class InventoryAdapter extends ListAdapter<Product, ProductViewHolder> {
    private ProductViewModel viewModel;
    public InventoryAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback,
                            ProductViewModel viewModel){
        super(diffCallback);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return ProductViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product currentProduct = getItem(position);
        holder.bind(currentProduct.productQuantity, currentProduct.productName, currentProduct.productSku);
        Button deleteButton = holder.getDeleteButton();
        deleteButton.setOnClickListener(view -> {
            viewModel.removeProduct(holder.getSkuToDelete());
        });
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
