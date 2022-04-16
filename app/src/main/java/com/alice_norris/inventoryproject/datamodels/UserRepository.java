package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alice_norris.inventoryproject.interfaces.UserDao;
import com.alice_norris.inventoryproject.utils.PasswordHasher;

import java.util.List;


//repository, set to use with the user database.
public class UserRepository {
    private UserDao repositoryUserDao;
    private int usersMatched;
    LiveData<User> matchedUser;

    //constructor
    public UserRepository(Application application){
        InventoryDatabase userDb = InventoryDatabase.getDatabase(application);
        repositoryUserDao = userDb.userDao();
        login("null", "null");
        matchedUser = getUser();
    }

    //runs login query
    public void login(String username, String password){
    LoginRunner loginRunner = new LoginRunner(username, password);
        //execute query
    InventoryDatabase.databaseWriteExecutor
            .execute(loginRunner);
    }

    //method for registering a new user
    public void register(User newUser){
        InventoryDatabase.databaseWriteExecutor
                .execute(() -> repositoryUserDao.addUser(newUser));
    }

    private class LoginRunner implements Runnable{
        private String username;
        private String password;

        LoginRunner(String username, String password){
            this.username = username;
            this.password = password;
        }
        public void run(){
            matchedUser = repositoryUserDao.login(username, password);
        }
    }

    public LiveData<User> getUser(){
        return matchedUser;
    }
}
