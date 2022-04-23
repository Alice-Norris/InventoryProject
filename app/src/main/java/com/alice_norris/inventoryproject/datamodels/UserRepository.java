package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.interfaces.UserDao;

//repository, set to use with the user database.
public class UserRepository {
    private UserDao loginUserDao;
    LiveData<User> matchedUser;

    //constructor
    public UserRepository(Application application){
        InventoryDatabase userDb = InventoryDatabase.getDatabase(application);
        loginUserDao = userDb.userDao();
    }

    //login query
    public LiveData<User> login(String username, String password){
        //execute query
        LiveData<User> returnedUser;
        InventoryDatabase.databaseWriteExecutor
            .execute(() -> {
                 matchedUser = loginUserDao.login(username, password);
            });
        return matchedUser;
    }

    //register query
    public void register(User newUser){
        InventoryDatabase.databaseWriteExecutor
                .execute(() ->{
                   loginUserDao.register(newUser);
                });

    }

    public LiveData<User> getUser(){
        return matchedUser;
    }


}
