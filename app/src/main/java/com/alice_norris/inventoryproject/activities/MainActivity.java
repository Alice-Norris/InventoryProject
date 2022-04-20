package com.alice_norris.inventoryproject.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
public class MainActivity extends AppCompatActivity{

    private ProductViewModel productViewModel;
    protected RecyclerView inventoryRecyclerView;
    protected InventoryAdapter inventoryAdapter;
    protected ActivityResultLauncher<Intent> loginResultLauncher;
    protected ActivityResultContracts.StartActivityForResult loginActivityForResult =
            new ActivityResultContracts.StartActivityForResult();
    protected LoginActivityResult loginActivityResult = new LoginActivityResult();
    protected MainViewModel mainViewModel;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call super constructor, get binding, set content
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting toolbar reference and setting it as the activity's actionbar
        Toolbar inventoryToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(inventoryToolbar);

        //floatingActionButton and setup
        FloatingActionButton addProductFloat = findViewById(R.id.add_button);
        addProductFloat.setOnClickListener(view -> {
            DialogFragment addItemDialog = new AddProductDialog();
            addItemDialog.show(getSupportFragmentManager(), "Add Item");
        });

        //creating Activity launcher for starting login activity
        loginResultLauncher = registerForActivityResult(
                loginActivityForResult, loginActivityResult);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        //creating inventory adapter, passing in a diffutil for when the data changes
        inventoryAdapter = new InventoryAdapter(new InventoryAdapter.ProductDiff(), productViewModel);

        //getting recyclerview reference, setting adapter, and layout manager
        inventoryRecyclerView = findViewById(R.id.main_inventory_list);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //creating product view model and attaching an observer
        productViewModel.getAllProducts()
            .observe(this, products -> {
                inventoryAdapter.submitList(products);
            });

        //creating navClickListener to open drawer
        drawerLayout = findViewById(R.id.layout_drawer_main);
            inventoryToolbar.setNavigationOnClickListener(view ->{
                drawerLayout.open();
        });

        //setting item selection listener on nav drawer
        navView = findViewById(R.id.menu_navigation);
        navView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_inventory){
                productViewModel.inventory();
                productViewModel.getAllProducts().observe(this, list ->{
                    inventoryAdapter.submitList(list);
                });
            }
            if (menuItem.getItemId() == R.id.nav_out_of_stock){
                productViewModel.out_of_stock();
                productViewModel.getZeroQtyProducts().observe(this, list->{
                    inventoryAdapter.submitList(list);
                });
            }
            if (menuItem.getItemId() == R.id.nav_log_out){
                mainViewModel.logout();
            } else {
            }
            drawerLayout.close();
            return true;
        });

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean status) {
                if (status != null && !status) {
                    startLoginActivity();
                }
            }
        });

//    public boolean dispatchTouchEvent (MotionEvent tap){
//        Rect navViewRect = new Rect();
//        navView.getGlobalVisibleRect(navViewRect);
//        if (tap.getX() > navViewRect.width() || tap.getY() > navViewRect.height()) {
//            navView.setVisibility(INVISIBLE);
//        }
//        return super.dispatchTouchEvent(tap);
//    }
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
                mainViewModel.loginStatusChange();
                if(result.getData().hasExtra("userFirstName")) {
                    String userFirstName = result.getData().getStringExtra("userFirstName");
                    mainViewModel.setUserFirstName(userFirstName);

                }
                MenuItem navGreeting = navView.getMenu().getItem(0);
                navGreeting.setTitle(
                        getString(R.string.nav_greeting, mainViewModel.getUserFirstName()));
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
                DialogFragment addProductDialog = new AddProductDialog();
                addProductDialog.show(getSupportFragmentManager(), "Add Item");
                return true;
            case R.id.removeItem:
                DialogFragment removeProductDialog = new RemoveProductDialog();
                removeProductDialog.show(getSupportFragmentManager(), "Remove Item");
                return true;
            case R.id.app_bar_switch:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.SEND_SMS);

                } else {
                    item.setChecked(false);
                }
                return true;
            default:
                return false;
        }
    }
}