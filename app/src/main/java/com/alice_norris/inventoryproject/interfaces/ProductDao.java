package com.alice_norris.inventoryproject.interfaces;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alice_norris.inventoryproject.datamodels.Product;

import java.util.List;


//interface to product table of database
@Dao
public interface ProductDao {

    //insert new product, ignore duplicate skus
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(Product product);

    //should return all orders by product sku, increasing.
    @Query("SELECT * FROM products ORDER BY product_sku ASC")
    LiveData<List<Product>> getProductsBySku();

    @Query("DELETE FROM products WHERE product_sku LIKE :productSku")
    void deleteProduct(String productSku);

    @Query("SELECT * FROM products WHERE qty IS '0'")
    LiveData<List<Product>> getZeroQtyProducts();

    @Query("SELECT * FROM products WHERE product_sku LIKE :productSku")
    Product getProductBySku(String productSku);

    @Update
    void updateProduct(Product product);
}
