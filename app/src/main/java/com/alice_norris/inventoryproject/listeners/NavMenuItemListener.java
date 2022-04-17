package com.alice_norris.inventoryproject.listeners;

import static android.view.View.INVISIBLE;

import android.view.MenuItem;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.adapters.InventoryAdapter;
import com.alice_norris.inventoryproject.datamodels.MainViewModel;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.google.android.material.navigation.NavigationView;

public class NavMenuItemListener implements NavigationView.OnNavigationItemSelectedListener{
    private int menuEntry;
    private NavigationView navView;
    private MainViewModel mainModel;
    private ProductViewModel productModel;
    private InventoryAdapter invAdapter;
    public NavMenuItemListener(NavigationView navView,
                               MainViewModel mainModel,
                               ProductViewModel productModel,
                               InventoryAdapter invAdapter) {

        this.navView = navView;
        this.mainModel = mainModel;
        this.productModel = productModel;
        this.invAdapter = invAdapter;
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        this.menuEntry = item.getItemId();
        navView.setVisibility(INVISIBLE);
        switch (menuEntry) {
            case R.id.nav_greeting:
                return true;
            case R.id.nav_inventory:
                invAdapter.submitList(productModel.getAllProducts().getValue());
                return true;
            case R.id.nav_out_of_stock:
                invAdapter.submitList(productModel.getZeroQtyProducts().getValue());
                return true;
            case R.id.nav_log_out:
                mainModel.logout();
                return true;
            default:
                return false;
        }
    }
}