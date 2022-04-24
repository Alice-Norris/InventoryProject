package com.alice_norris.inventoryproject.utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alice_norris.inventoryproject.activities.MainActivity;
import com.alice_norris.inventoryproject.datamodels.Product;
import com.alice_norris.inventoryproject.datamodels.ProductRepository;
import com.alice_norris.inventoryproject.datamodels.ProductViewModel;

public class InventoryIntentService extends IntentService {
    private NotificationManager notificationManager;
    private static final String ZERO_QTY_ITEM = "com.alice_norris.inventoryproject.extra.PRODUCT";
    private final String CHANNEL_ID_INVENTORY = "channel_inventory";
    public InventoryIntentService() {
        super("InventoryIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Product product = intent.getParcelableExtra(ZERO_QTY_ITEM);
            createInventoryNotification(product);
        }
    }

    private void createNotificationChannel(){
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