package com.alice_norris.inventoryproject.utils;

import android.os.Bundle;

import com.alice_norris.inventoryproject.datamodels.Product;

public interface AdapterEventListener{

    void removeItem (int position);

    void changeItem (int position);

}
