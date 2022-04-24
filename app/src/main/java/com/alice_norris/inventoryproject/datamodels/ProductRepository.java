package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alice_norris.inventoryproject.datamodels.InventoryDatabase;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.interfaces.ProductDao;

import java.util.List;


public class ProductRepository {
    private final ProductDao productDao;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Product>> zeroQtyProducts;
    private Product requestedProduct;
    private MutableLiveData<Product> lastZeroProduct;

    public ProductRepository(Application application) {
        InventoryDatabase ProductDb = InventoryDatabase.getDatabase(application);
        productDao = ProductDb.productDao();
        allProducts = productDao.getProductsBySku();
        zeroQtyProducts = productDao.getZeroQtyProducts();
        lastZeroProduct = new MutableLiveData<>();
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
        if(Integer.valueOf(product.productQuantity) == 0){
            this.lastZeroProduct.setValue(product);
        }
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

    public LiveData<Product> getLastZeroProduct(){ return lastZeroProduct; }
}
