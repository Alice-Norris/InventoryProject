package com.alice_norris.inventoryproject.datamodels;

import static java.lang.Integer.valueOf;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;


public class ProductViewModel extends AndroidViewModel {
    private LiveData<List<Product>> allProducts;
    private final LiveData<List<Product>> zeroQtyProducts;
    private Product requestedProduct;
    private final ProductRepository productRepository;
    private final MutableLiveData<Product> lastZeroProduct;

    public ProductViewModel(Application application){
        super(application);
        this.productRepository = new ProductRepository(application);
        this.allProducts = productRepository.getAllProducts();
        this.zeroQtyProducts = productRepository.getZeroQtyProducts();
        this.requestedProduct = productRepository.getProductBySku("");
        this.lastZeroProduct= new MutableLiveData<>();
    }

    //********* functions to return current values *********//
    public LiveData<List<Product>> getAllProducts() { return allProducts;}

    public LiveData<List<Product>> getZeroQtyProducts() {return zeroQtyProducts;}

    public Product getRequestedProduct() {return requestedProduct;}
    //********* CRUD Operations *********//
    public void addProduct(String sku, String name, String qty) {
        Product newProduct = new Product(sku, name, qty);
        productRepository.insertProduct(newProduct);
    }

    public Product getProductBySku (String sku) {
        this.requestedProduct = productRepository.getProductBySku(sku);
        return this.requestedProduct;
    }

    public void updateProduct(Product product){
        if(Integer.valueOf(product.productQuantity) == 0){
            lastZeroProduct.setValue(product);
        }
        productRepository.updateProduct(product);}

    public void removeProduct(String sku){
        productRepository.deleteProduct(sku);
    }

    public MutableLiveData<Product> getLastZeroProduct(){
        return this.lastZeroProduct;
    }
    //********* functions that refresh lists *********//

    public void inventory(){
        productRepository.refreshProducts();
        this.allProducts = productRepository.getAllProducts();
    }

    public void out_of_stock(){
        productRepository.refreshZeroQtyProducts();
        this.allProducts = productRepository.getZeroQtyProducts();
    }
}
