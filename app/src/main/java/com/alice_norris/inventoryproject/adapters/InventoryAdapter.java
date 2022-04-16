package com.alice_norris.inventoryproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.R;
import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends ListAdapter<Product, InventoryAdapter.ViewHolder> {
    private LiveData<List<Product>> products = null;

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemQty;
        private final TextView itemName;
        private final TextView itemSku;

        public ViewHolder(View view) {
            super(view);

            this.itemQty = view.findViewById(R.id.entryProductQty);
            this.itemName = view.findViewById(R.id.entryProductName);
            this.itemSku = view.findViewById(R.id.entryProductSku);
        }

        public void bind(String quantity, String name, String sku) {
            itemQty.setText(quantity);
            itemName.setText(name);
            itemSku.setText(sku);
        }
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
    public InventoryAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.inventory_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if ( products == null) {
            this.products = createEmptyProducts();
        } else {
        Product currentProduct = products.getValue().get(position);
        holder.bind(currentProduct.productQuantity, currentProduct.productName, currentProduct.productSku);
        }
    }

    @Override
    public int getItemCount(){
        if (products != null) {
            return products.getValue().size();
        } else {
            return 0;
        }
    }

    public void setProducts(LiveData<List<Product>> productData){
        this.products = productData;
    }

    public LiveData<List<Product>> createEmptyProducts(){
        List<Product> emptyProductList = new ArrayList<>();
        while (emptyProductList.size() < 27) {
            emptyProductList.add(new Product());
        }
        MutableLiveData<List<Product>> emptyProductLiveData = new MutableLiveData<>();
        emptyProductLiveData.setValue(emptyProductList);
        return emptyProductLiveData;
    }

}
