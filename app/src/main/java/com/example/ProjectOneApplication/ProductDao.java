package com.example.ProjectOneApplication;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    //insert new product, ignore duplicate skus
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(Product product);

    //should return all orders by product sku, increasing.
    @Query("SELECT * FROM product ORDER BY product_sku ASC")
    LiveData<List<Product>> getProductsBySku();
}
