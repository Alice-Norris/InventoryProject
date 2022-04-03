package com.example.ProjectOneApplication;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey
    public int itemId;

    @ColumnInfo(name="product_sku")
    String productSku;

    @ColumnInfo(name="product_name")
    String productName;

    @ColumnInfo(name="qty")
    int itemQuantity;
}
