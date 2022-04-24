package com.alice_norris.inventoryproject.utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductRepository;

public class InventoryIntentService extends IntentService {

    public InventoryIntentService(){

    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Product product = intent.getParcelableExtra("product");
        ProductRepository productRepository = new ProductRepository(getApplication());
        }

    private void sendNotification(Product product){
        final String CHANNEL_ID_INVENTORY = "channel_inventory";
        NotificationManager notificationManager;
        CharSequence name = "Inventory";
        String description = "Out of stock notifications for inventory";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_INVENTORY, name, importance);
        channel.setDescription(description);
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_INVENTORY)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Out of stock")
                .setContentText("SKU " + product.productSku + ", " + product.productName + " is out of stock!")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(0, notification);
    }
}
