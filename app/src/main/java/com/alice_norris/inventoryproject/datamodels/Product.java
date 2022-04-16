package com.alice_norris.inventoryproject.datamodels;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
//defining the product table in the database.
@Entity
public class Product {

    public Product(){
        this.productSku="";
        this.productName="";
        this.productQuantity="";
    }

    @PrimaryKey
    public int itemId;

    @ColumnInfo(name="product_sku")
    public String productSku;

    @ColumnInfo(name="product_name")
    public String productName;

    @ColumnInfo(name="qty")
    public String productQuantity;

}
