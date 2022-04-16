package com.alice_norris.inventoryproject.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alice_norris.inventoryproject.R;
import com.alice_norris.inventoryproject.adapters.InventoryAdapter;
import com.alice_norris.inventoryproject.databinding.ActivityMainBinding;
import com.alice_norris.inventoryproject.datamodels.MainViewModel;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.google.android.material.navigation.NavigationView;


//primary activity, used to view inventory
public class MainActivity extends AppCompatActivity {

    private ProductViewModel productViewModel;
    protected RecyclerView inventoryRecyclerView;
    protected InventoryAdapter inventoryAdapter;
    protected NavClickListener navClickListener;
    protected ActivityResultLauncher<Intent> loginResultLauncher;
    protected ActivityResultContracts.StartActivityForResult loginActivityForResult =
            new ActivityResultContracts.StartActivityForResult();
    protected LoginActivityResult loginActivityResult = new LoginActivityResult();
    protected MainViewModel mainModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call super constructor, get binding, set content
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //getting toolbar reference and setting it as the activity's actionbar
        Toolbar inventoryToolbar = findViewById(R.id.inventoryToolbar);
        setSupportActionBar(inventoryToolbar);

        //creating Activity launcher for starting login activity
        loginResultLauncher = registerForActivityResult(
                loginActivityForResult,loginActivityResult);

        //creating inventory adapter, passing in a diffutil for when the data changes
        inventoryAdapter = new InventoryAdapter(new InventoryAdapter.ProductDiff());

        //getting recyclerview reference, setting adapter, and layout manager
        inventoryRecyclerView = findViewById(R.id.inventoryList);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //fill list with empty products
        inventoryAdapter.setProducts(inventoryAdapter.createEmptyProducts());

        //creating product view model and attaching an observer
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        productViewModel.getAllProducts()
                 .observe(this, products -> inventoryAdapter.submitList(products));

        //creating navClickListener, setting appBar
        navClickListener = new NavClickListener();

        inventoryToolbar.setNavigationOnClickListener(navClickListener);

        mainModel= new ViewModelProvider(this).get(MainViewModel.class);
        mainModel.getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean status){
                if (!status){
                    startLoginActivity();
                }
            }
        });
        //UserViewModel loginUserViewModel = ViewModelProviders.of
    }

//    protected void onStart(){
//        super.onStart();
//        Intent startIntent = getIntent();
//        if(mainViewModel == true) { ;
//            startLoginActivity();
//        }
//    }

//    protected void onResume(){
//        super.onResume();
//        if(this.loggedIn == false) {
//            startLoginActivity();
//        }
//    }
    public void startLoginActivity(){
        Intent loginIntent = new ActivityResultContracts.StartActivityForResult().createIntent(getApplicationContext(), new Intent());
        loginIntent.setClass(getApplicationContext(), LoginActivity.class);
        loginResultLauncher.launch(loginIntent);
    }

    public class LoginActivityResult implements ActivityResultCallback<ActivityResult>{

        public void onActivityResult(ActivityResult result){
            if (result.getResultCode() == Activity.RESULT_OK){
                mainModel.loginStatusChange();
            }
        }

    }

    public static class NavClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            NavigationView navMenu = view.findViewById(R.id.nav_menu);
            toggleNavVisibility(navMenu);
        }

        public void toggleNavVisibility(NavigationView navMenu) {
            if (navMenu.getVisibility() == View.GONE) {
                navMenu.setVisibility(View.VISIBLE);
            } else {
                navMenu.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case R.id.nav_inventory:
//                productViewModel.getAllProducts();
//                return true;
//            case R.id.nav_out_of_stock:
//                productViewModel.getZeroQtyProducts();
//                return true;
//            case R.id.nav_log_out:
//                if (this.currentUser != null){
//                    this.currentUser = null;
//                    Intent intent = new Intent(this, MainActivity.class);
//                    startActivity(intent);
//                }
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}