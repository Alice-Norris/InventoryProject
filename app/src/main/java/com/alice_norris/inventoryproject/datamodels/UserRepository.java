package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.interfaces.UserDao;
import com.alice_norris.inventoryproject.utils.PasswordHasher;

import java.util.List;


//repository, set to use with the user database.
public class UserRepository {
    private UserDao loginUserDao;
    LiveData<User> matchedUser;

    //constructor
    public UserRepository(Application application){
        InventoryDatabase userDb = InventoryDatabase.getDatabase(application);
        loginUserDao = userDb.userDao();
        matchedUser = loginUserDao.login("null", "null");

        if(matchedUser.getValue() == null){
            Log.d("!!!RETURNED-DATA!!!", "fuck you");
        } else if (matchedUser.getValue() != null) {
            Log.d("!!!RETURNED_DATA!!!", matchedUser.getValue().toString());
        } else {
            Log.d("!!!RETURNED_DATA!!!", matchedUser.toString());
        }
    }

    //login query
    public void login(String username, String password){
        Log.d("!!!PASSED_DATA", username+" "+password);
        //execute query
    InventoryDatabase.databaseWriteExecutor
            .execute(() -> {
                matchedUser = loginUserDao.login(username, password);
                Log.d("!!!RETURNED_DATA", matchedUser.toString());
                if (matchedUser.getValue() == null) {
                    Log.d("!!!RETURNED-DATA!!!", "GOT NULL BACK!!!");
                } else if(matchedUser.getValue() != null){
                    Log.d("!!!RETURNED_DATA!!!", matchedUser.getValue().toString());
                } else {
                    Log.d("!!!RETURNED_DATA!!!", matchedUser.toString());
                }

            });
    }

    //register query
    public void register(User newUser){
        InventoryDatabase.databaseWriteExecutor
                .execute(() ->{
                   loginUserDao.register(newUser);
                });
    }

    public LiveData<User> getUser(){
        if (matchedUser != null) {
            return matchedUser;
        } else {
            return null;
        }
    }

}
