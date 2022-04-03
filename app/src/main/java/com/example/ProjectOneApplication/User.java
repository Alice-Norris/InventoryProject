package com.example.ProjectOneApplication;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="users")
public class User{
    public User(@NonNull String username, @NonNull String userFirstName, @NonNull String userLastName, @NonNull String passwdHash){
        this.username = username;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.passwdHash = passwdHash;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="user_id")
    public int userId;

    @ColumnInfo (name="username")
    String username;

    @ColumnInfo(name="first_name")
    String userFirstName;

    @ColumnInfo(name="last_name")
    String userLastName;

    @ColumnInfo(name="password_hash")
    String passwdHash;
}

