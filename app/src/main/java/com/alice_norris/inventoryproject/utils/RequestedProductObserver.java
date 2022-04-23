package com.alice_norris.inventoryproject.utils;

import androidx.lifecycle.Observer;

import com.alice_norris.inventoryproject.datamodels.Product;

public class RequestedProductObserver implements Observer<Product> {
    Product requestedProduct;
    @Override
    public void onChanged(Product product) {
        this.requestedProduct = product;
    }

    public Product getRequestedProduct(){
        return this.requestedProduct;
    }
}
