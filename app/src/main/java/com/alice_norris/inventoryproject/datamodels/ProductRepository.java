package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.datamodels.InventoryDatabase;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.interfaces.ProductDao;

import java.util.List;

public class ProductRepository {
    private final ProductDao productDao;
    private final LiveData<List<Product>> allProducts;
    private final LiveData<List<Product>> zeroQtyProducts;

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

    void deleteProduct(Product product){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> productDao.deleteProduct(product.productSku));
    }

    LiveData<List<Product>> getZeroQtyProducts(){ return zeroQtyProducts; }
}
