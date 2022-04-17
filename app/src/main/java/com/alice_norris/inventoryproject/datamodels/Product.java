package com.alice_norris.inventoryproject.datamodels;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//defining the product table in the database.
@Entity(tableName = "products")
public class Product {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="product_sku")
    public String productSku;

    @NonNull
    @ColumnInfo(name="product_name")
    public String productName;

    @NonNull
    @ColumnInfo(name="qty")
    public String productQuantity;

    public Product(String productSku, String productName, String productQuantity){
        this.productSku=productSku;
        this.productName=productName;
        this.productQuantity=productQuantity;
    }

}
