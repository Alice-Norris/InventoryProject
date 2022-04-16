package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private final LiveData<List<Product>> allProducts;
    private final LiveData<List<Product>> zeroQtyProducts;
    private final ProductRepository productRepository;
    public ProductViewModel(Application application){
        super(application);
        this.productRepository = new ProductRepository(application);
        this.allProducts = productRepository.getAllProducts();
        this.zeroQtyProducts = productRepository.getZeroQtyProducts();
    }

    public LiveData<List<Product>> getAllProducts() { return allProducts;}

    public LiveData<List<Product>> getZeroQtyProducts() {return zeroQtyProducts;}
}
