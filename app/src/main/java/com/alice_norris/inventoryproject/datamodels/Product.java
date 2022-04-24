package com.alice_norris.inventoryproject.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

//defining the product table in the database.
@Entity(tableName = "products")
public class Product implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="product_sku")
    public final String productSku;

    @NonNull
    @ColumnInfo(name="product_name")
    public String productName;

    @NonNull
    @ColumnInfo(name="qty")
    public String productQuantity;

    public Product(@NonNull String productSku,
                   @NonNull String productName,
                   @NonNull String productQuantity){
        this.productSku=productSku;
        this.productName=productName;
        this.productQuantity=productQuantity;
    }

    @Ignore
    public Product(Product constructorProduct){
        this.productSku = constructorProduct.productSku;
        this.productName = constructorProduct.productName;
        this.productQuantity = constructorProduct.productQuantity;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productSku);
        parcel.writeString(productName);
        parcel.writeString(productQuantity);
    }

    public Product(Parcel in) {
        productSku = in.readString();
        productName = in.readString();
        productQuantity = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>(){

        @Override
        public Product createFromParcel(Parcel parcel) {
            return new Product(parcel);
        }

        @Override
        public Product[] newArray(int i) {
            return new Product[i];
        }
    };
}
