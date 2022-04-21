package com.alice_norris.inventoryproject.datamodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alice_norris.inventoryproject.interfaces.ProductDao;
import com.alice_norris.inventoryproject.interfaces.UserDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//define Database, with two tables, user and product tables.
//the .class files define the structure of each table.
@Database(entities = {com.alice_norris.inventoryproject.datamodels.User.class,
        com.alice_norris.inventoryproject.datamodels.Product.class},
        version = 1,
        exportSchema = false)
public abstract class InventoryDatabase extends RoomDatabase {

    //creating data access objects for future use
    public abstract UserDao userDao();
    public abstract ProductDao productDao();

    //constants for database creation
    private static volatile InventoryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    //create database if none exists
    static InventoryDatabase getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (InventoryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    InventoryDatabase.class,
                                              "inventory_database")
                            .addCallback(populateUserDatabase)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback populateUserDatabase = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                UserDao userDao = INSTANCE.userDao();
                userDao.register(new User("null","null","null","null"));
                ProductDao productDao = INSTANCE.productDao();
                Product testProduct1 = new Product("015834", "Strawberry", "20");
                Product testProduct2 = new Product("583948", "Kiwi", "12");
                Product testProduct3 = new Product("438275", "Cantaloupe", "5");
                productDao.addProduct(testProduct1);
                productDao.addProduct(testProduct2);
                productDao.addProduct(testProduct3);
            });
        }
    };
}
