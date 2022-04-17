package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.datamodels.InventoryDatabase;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.interfaces.ProductDao;

import java.util.List;

public class ProductRepository {
    private final ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> zeroQtyProducts;
    private LiveData<Product> requestedProduct;

    public ProductRepository(Application application) {
        InventoryDatabase ProductDb = InventoryDatabase.getDatabase(application);
        productDao = ProductDb.productDao();
        allProducts = productDao.getProductsBySku();
        zeroQtyProducts = productDao.getZeroQtyProducts();
    }

    LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }

    void insertProduct(Product product){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.addProduct(product));
    }

    void deleteProduct(String sku){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.deleteProduct(sku));
    }

    void getProductsBySku(){
        this.allProducts = productDao.getProductsBySku();
    }

    LiveData<Product> getItemBySku(String sku){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> {
                    requestedProduct = productDao.getProductBySku(sku);
                });
        return requestedProduct;
    }

    LiveData<List<Product>> getZeroQtyProducts(){ return zeroQtyProducts; }
}
