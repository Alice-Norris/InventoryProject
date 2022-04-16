package com.alice_norris.inventoryproject.datamodels;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alice_norris.inventoryproject.interfaces.ProductDao;
import com.alice_norris.inventoryproject.interfaces.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//define Database, with two tables, user and product tables.
//the .class files define the structure of each table.
@Database(entities = {com.alice_norris.inventoryproject.datamodels.User.class, com.alice_norris.inventoryproject.datamodels.Product.class}, version = 1, exportSchema = false)
public abstract class InventoryDatabase extends RoomDatabase {

    //creating data access objects for future use
    public abstract UserDao userDao();
    public abstract ProductDao productDao();

    //constants for database creation
    private static volatile InventoryDatabase INSTANCE;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);
    //create database if none exists
    static InventoryDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (InventoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    InventoryDatabase.class,
                                              "inventory_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
