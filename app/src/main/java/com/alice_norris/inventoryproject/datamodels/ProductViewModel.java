package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> zeroQtyProducts;
    private final ProductRepository productRepository;
    public ProductViewModel(Application application){
        super(application);
        this.productRepository = new ProductRepository(application);
        this.allProducts = productRepository.getAllProducts();
        this.zeroQtyProducts = productRepository.getZeroQtyProducts();
    }

    public LiveData<List<Product>> getAllProducts() { return allProducts;}

    public LiveData<List<Product>> getZeroQtyProducts() {return zeroQtyProducts;}

    public void addProduct(String sku, String name, String qty) {
        Product newProduct = new Product(sku, name, qty);
        productRepository.insertProduct(newProduct);
    }

    public void removeProduct(String sku){
        productRepository.deleteProduct(sku);
    }

    public void inventory(){
        this.allProducts = productRepository.getAllProducts();
    }

    public void out_of_stock(){
        this.zeroQtyProducts = getZeroQtyProducts();
    }
}
