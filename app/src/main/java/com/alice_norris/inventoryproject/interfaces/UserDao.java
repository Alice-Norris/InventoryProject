package com.alice_norris.inventoryproject.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.alice_norris.inventoryproject.datamodels.User;

//interface to user table of database
@Dao
public interface UserDao {

    //method for new user, ignores user if conflicts with existing entry
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void register(User newUser);

    //method for logging in, returns the number of records that match the entered
    //username and password hash. For successful login, should be 1.
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    public LiveData<User> login(String username, String password);

}
