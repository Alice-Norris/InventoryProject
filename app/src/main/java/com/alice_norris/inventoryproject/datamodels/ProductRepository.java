package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.datamodels.InventoryDatabase;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.interfaces.ProductDao;

import java.util.List;

import io.reactivex.Single;

public class ProductRepository {
    private final ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> zeroQtyProducts;
    private Product requestedProduct;
    public ProductRepository(Application application) {
        InventoryDatabase ProductDb = InventoryDatabase.getDatabase(application);
        productDao = ProductDb.productDao();
        allProducts = productDao.getProductsBySku();
        zeroQtyProducts = productDao.getZeroQtyProducts();
    }

    void insertProduct(Product product){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.addProduct(product));
    }

    Product getProductBySku(String sku){
       InventoryDatabase.databaseWriteExecutor
               .execute(() -> {
                   requestedProduct = productDao.getProductBySku(sku);
               });
       return this.requestedProduct;
    }

    void updateProduct(Product product){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.updateProduct(product));
    }
    void deleteProduct(String sku){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.deleteProduct(sku));
    }

    void refreshProducts(){
        this.allProducts = productDao.getProductsBySku();
    }

    void refreshZeroQtyProducts() { this.zeroQtyProducts = productDao.getZeroQtyProducts(); }

    LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }

    LiveData<List<Product>> getZeroQtyProducts(){ return zeroQtyProducts; }
}
