package com.example.ProjectOneApplication;

import android.app.Application;

public class UserRepository {
    private UserDao mUserDao;

    UserRepository(Application application){
        InventoryDatabase userDb = InventoryDatabase.getDatabase(application);
    }
}
