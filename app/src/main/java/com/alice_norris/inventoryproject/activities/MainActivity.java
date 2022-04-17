package com.alice_norris.inventoryproject.activities;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.adapters.InventoryAdapter;
import com.alice_norris.inventoryproject.databinding.ActivityMainBinding;
import com.alice_norris.inventoryproject.datamodels.MainViewModel;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.alice_norris.inventoryproject.fragments.AddProductDialog;
import com.alice_norris.inventoryproject.fragments.RemoveProductDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


//primary activity, used to view inventory
public class MainActivity extends AppCompatActivity implements AddProductDialog.AddItemDialogListener, RemoveProductDialog.RemoveItemDialogListener{

    private ProductViewModel productViewModel;
    protected RecyclerView inventoryRecyclerView;
    protected InventoryAdapter inventoryAdapter;
    protected ActivityResultLauncher<Intent> loginResultLauncher;
    protected ActivityResultContracts.StartActivityForResult loginActivityForResult =
            new ActivityResultContracts.StartActivityForResult();
    protected LoginActivityResult loginActivityResult = new LoginActivityResult();
    protected MainViewModel mainModel;
    protected NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call super constructor, get binding, set content
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting toolbar reference and setting it as the activity's actionbar
        Toolbar inventoryToolbar = findViewById(R.id.inventoryToolbar);
        setSupportActionBar(inventoryToolbar);

        //floatingActionButton and setup
        FloatingActionButton addProductFloat = findViewById(R.id.add_button);
        addProductFloat.setOnClickListener(view -> {
            DialogFragment addItemDialog = new AddProductDialog();
            addItemDialog.show(getSupportFragmentManager(), "Add Item");
        });

        //creating Activity launcher for starting login activity
        loginResultLauncher = registerForActivityResult(
                loginActivityForResult,loginActivityResult);

        //creating inventory adapter, passing in a diffutil for when the data changes
        inventoryAdapter = new InventoryAdapter(new InventoryAdapter.ProductDiff());

        //getting recyclerview reference, setting adapter, and layout manager
        inventoryRecyclerView = findViewById(R.id.inventoryList);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //creating product view model and attaching an observer
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts()
                 .observe(this, products -> {
                     inventoryAdapter.submitList(products);
                 });

        //creating navClickListener, setting appBar
        inventoryToolbar.setNavigationOnClickListener(view ->{
            if (navView.getVisibility() == INVISIBLE){
                navView.setVisibility(VISIBLE);
            } else {
                navView.setVisibility(INVISIBLE);
            }
        });

        mainModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainModel.getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean status){
                if (status != null && !status){
                    startLoginActivity();
                }
            }
        });

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(item -> {
            navView.setVisibility(INVISIBLE);
            switch (item.getItemId()) {
                case R.id.nav_greeting:
                    return true;
                case R.id.nav_inventory:
                    productViewModel.inventory();
                    return true;
                case R.id.nav_out_of_stock:
                    productViewModel.out_of_stock();
                    return true;
                case R.id.nav_log_out:
                    mainModel.logout();
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        productViewModel.getAllProducts().observe(this, list-> inventoryAdapter.submitList(list));
    }
    public void startLoginActivity(){
        Intent loginIntent = new ActivityResultContracts.StartActivityForResult().createIntent(getApplicationContext(), new Intent());
        loginIntent.setClass(getApplicationContext(), LoginActivity.class);
        loginResultLauncher.launch(loginIntent);
    }

    public class LoginActivityResult implements ActivityResultCallback<ActivityResult>{
        public void onActivityResult(ActivityResult result){
            if (result.getResultCode() == Activity.RESULT_OK){
                mainModel.loginStatusChange();
                if(result.getData() != null && result.getData().hasExtra("userFirstName")) {
                    String userFirstName = result.getData().getStringExtra("userFirstName");
                    mainModel.setUserFirstName(userFirstName);
                }
                NavigationView navView = findViewById(R.id.nav_view);
                MenuItem navGreeting = navView.getMenu().getItem(0);
                navGreeting.setTitle(
                        getString(R.string.nav_menu_greeting, mainModel.getUserFirstName()));
            }
        }
    }

    //Creating option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.addItem:
                Log.d("!!!MENU CLICK!!!", String.valueOf(itemId));
                DialogFragment addProductDialog = new AddProductDialog();
                addProductDialog.show(getSupportFragmentManager(), "Add Item");
                return true;
            case R.id.removeItem:
                Log.d("!!!MENU CLICK!!!", String.valueOf(itemId));
                DialogFragment removeProductDialog = new RemoveProductDialog();
                removeProductDialog.show(getSupportFragmentManager(), "Remove Item");
                return true;
            case R.id.app_bar_switch:
                Log.d("!!!MENU CLICK!!!", String.valueOf(itemId));
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onDialogPositiveClick(String sku, String name, String qty){
        productViewModel.addProduct(sku, name, qty);
    }

    @Override
    public void onRemovePositiveClick(String sku){
        productViewModel.removeProduct(sku);
    }
}