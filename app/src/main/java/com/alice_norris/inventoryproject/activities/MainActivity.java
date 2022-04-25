package com.alice_norris.inventoryproject.activities;

import static android.app.Notification.CATEGORY_STATUS;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.fragments.AllowNotificationDialog;
import com.alice_norris.inventoryproject.fragments.GetItemDialog;
import com.alice_norris.inventoryproject.fragments.RemoveProductWarningDialog;
import com.alice_norris.inventoryproject.fragments.UpdateProductDialog;
import com.alice_norris.inventoryproject.utils.AdapterEventListener;
import com.alice_norris.inventoryproject.utils.LoginResultContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.alice_norris.inventoryproject.adapters.InventoryAdapter;
import com.alice_norris.inventoryproject.datamodels.MainViewModel;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;
import com.alice_norris.inventoryproject.fragments.AddProductDialog;
import com.alice_norris.inventoryproject.fragments.RemoveProductDialog;
import com.alice_norris.inventoryproject.R;

//primary activity, used to view inventory
public class MainActivity extends AppCompatActivity implements AdapterEventListener,
        AllowNotificationDialog.NotificationDialogListener {
    final String CHANNEL_ID_INVENTORY = "channel_inventory";
    private NotificationManager notificationManager;
    private ProductViewModel productViewModel;
    protected RecyclerView inventoryRecyclerView;
    protected InventoryAdapter inventoryAdapter;
    protected MainViewModel mainViewModel;
    protected NavigationView navView;
    protected DrawerLayout drawerLayout;
    private ActivityResultLauncher<String> permissionsResultLauncher;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //call super constructor, get binding, set content
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting toolbar reference and setting it as the activity's actionbar
        Toolbar inventoryToolbar = findViewById(R.id.main_toolbar);
        createNotificationChannel();
        setSupportActionBar(inventoryToolbar);
        //creating navClickListener to open drawer
        drawerLayout = findViewById(R.id.layout_drawer_main);
        inventoryToolbar.setNavigationOnClickListener(view -> drawerLayout.open());

        //floatingActionButton and setup
        FloatingActionButton addProductFloatButton = findViewById(R.id.add_button);
        addProductFloatButton.setOnClickListener(view -> {
            DialogFragment addItemDialog = new AddProductDialog();
            addItemDialog.show(getSupportFragmentManager(), "Add Item");
        });
        //getting user preferences
        preferences = getApplicationContext().getSharedPreferences("inventoryPreferences", 0);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setPreferenceReference(preferences);
        int permission = ContextCompat.checkSelfPermission(this,  Manifest.permission.SEND_SMS);
        if (permission == PERMISSION_GRANTED){
            mainViewModel.setNotificationPermission(true);
        }

        ActivityResultLauncher<Void> loginLauncher = createLoginActivityLauncher();
        mainViewModel.getLoginStatus().observe(this, status -> {
            if (!status) {
                loginLauncher.launch(null);
            }
        });
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productViewModel.getLastZeroProduct().observeForever(zeroProduct ->{
            if(mainViewModel.getNotificationPreference()){
                createInventoryNotification(zeroProduct);
            }
        });

        //creating inventory adapter, passing in a diffutil for when the data changes
        inventoryAdapter = new InventoryAdapter(new InventoryAdapter.ProductDiff(), this);
        inventoryAdapter.setHasStableIds(true);


        //getting recyclerview reference, setting adapter and layout manager
        inventoryRecyclerView = findViewById(R.id.main_inventory_list);
        inventoryRecyclerView.setAdapter(inventoryAdapter);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //creating product view model and attaching an observer
        productViewModel.getAllProducts()
                .observe(this, products -> inventoryAdapter.submitList(products));
        //setting item selection listener on nav drawer
        navView = findViewById(R.id.menu_navigation);
        setupNavMenu(navView);

         permissionsResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted ->{
                    if(isGranted){

                    } else {

                    }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        productViewModel.getAllProducts().observe(this,
                list-> inventoryAdapter.submitList(list));
    }

    //Creating option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        menu.getItem(3).setChecked(mainViewModel.getNotificationPreference());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addItem) {
            DialogFragment addProductDialog = new AddProductDialog();
            addProductDialog.show(getSupportFragmentManager(), "Add Item");
            return true;
        } else if (itemId == R.id.removeItem) {
            DialogFragment removeProductDialog = new RemoveProductDialog();
            removeProductDialog.show(getSupportFragmentManager(), "Remove Item");
            return true;
        } else if (itemId == R.id.update_item){
            DialogFragment getItemDialog = new GetItemDialog();
            getItemDialog.show(getSupportFragmentManager(), "Get Item");
        } else if (itemId == R.id.app_bar_switch) {
            if (!item.isChecked()) {
                int permission = ContextCompat.checkSelfPermission(this,  Manifest.permission.SEND_SMS);
                if (permission == PERMISSION_GRANTED){
                    mainViewModel.setNotificationPermission(true);
                }
                if(mainViewModel.getNotificationPermission()) {
                    mainViewModel.setNotificationPreference(true);
                    item.setChecked(true);
                    //code to start notifications

                } else {
                    getSmsPermission();
                    //code to start notifications
                }
            } else {
                item.setChecked(false);
                mainViewModel.setNotificationPreference(false);
                //code to stop notifications
            }
        }
        return true;
    }
    private void getSmsPermission(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.SEND_SMS) == PERMISSION_GRANTED){
                ///do permission stuff
        } else //if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS))
            {
                AllowNotificationDialog dialog = new AllowNotificationDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
    }

    private ActivityResultLauncher<Void> createLoginActivityLauncher(){
        //creating Activity launcher for starting login activity
        LoginResultContract loginContract = new LoginResultContract();
        loginContract.createIntent(getApplicationContext(), null);
        return registerForActivityResult(loginContract,
                result -> {
                    mainViewModel.setUserFirstName(result);
                    mainViewModel.loginStatusChange();
                    String navGreetingText = getString(R.string.nav_greeting, result);
                    String navGreetingDesc = getString(R.string.nav_greeting_desc, result);
                    MenuItem nav_greeting = navView.getMenu().getItem(0);
                    nav_greeting.setTitle(navGreetingText);
                    nav_greeting.setContentDescription(navGreetingDesc);
                });
    }

    public void setupNavMenu(NavigationView navView){
        navView.setNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == R.id.nav_inventory){
                productViewModel.inventory();
                productViewModel.getAllProducts().observe(this,
                        list -> inventoryAdapter.submitList(list));
            } else if (menuItem.getItemId() == R.id.nav_out_of_stock){
                productViewModel.out_of_stock();
                productViewModel.getZeroQtyProducts().observe(this,
                        list-> inventoryAdapter.submitList(list));
            } else if (menuItem.getItemId() == R.id.nav_log_out){
                mainViewModel.logout();
            }
            drawerLayout.close();
            return true;
        });
    }

    @Override
    public void removeItem(int position) {
        View itemEntry = inventoryRecyclerView.getChildAt(position);
        TextView itemSku = itemEntry.findViewById(R.id.entryProductSku);
        String sku = itemSku.getText().toString();
        productViewModel.getProductBySku(sku);
        Product product = productViewModel.getRequestedProduct();
        Bundle dialogBundle = new Bundle();
        dialogBundle.putString("sku", sku);
        RemoveProductWarningDialog dialog = new RemoveProductWarningDialog();
        dialog.setArguments(dialogBundle);
        dialog.show(getSupportFragmentManager(), "Removal Warning");
    }

    @Override
    public void changeItem(int position) {
        View itemEntry = inventoryRecyclerView.getChildAt(position);
        TextView itemSku = itemEntry.findViewById(R.id.entryProductSku);
        String sku = itemSku.getText().toString();
        productViewModel.getProductBySku(sku);
        Bundle dialogBundle = new Bundle();
        dialogBundle.putString("sku", sku);
        UpdateProductDialog dialog = new UpdateProductDialog();
        dialog.setArguments(dialogBundle);
        dialog.show(getSupportFragmentManager(), "Update Item");
    }

    @Override
    public void onNotificationDialogAllow(DialogFragment dialog) {
        requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 101);
    }

    @Override
    public void onNotificationDialogDeny(DialogFragment dialog) {
        return;
    }

    private void createNotificationChannel(){

        final String ZERO_QTY_ITEM = "com.alice_norris.inventoryproject.extra.PRODUCT";

        if (Build.VERSION.SDK_INT < 26) return;
        CharSequence name = "inventory";
        String description = "Out of stock notification";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_INVENTORY, name, importance);
        channel.setDescription(description);

        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void createInventoryNotification(Product product){
        final int NOTIFICATION_ID = 0;
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_INVENTORY)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Out of stock!")
                .setContentText(product.productName)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}