package com.example.ProjectOneApplication;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    //method for new user, ignores user if conflicts with existing entry
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(User newUser);

    //method for logging in, returns the number of records that match the entered
    //username and password hash. For successful login, should be 1.
    @Query("SELECT COUNT(*) FROM users WHERE username == :username AND password_hash == :passwdHash")
    int loginCheck(String username, String passwdHash);
}
