package com.alice_norris.inventoryproject.datamodels;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//defines the user table in the database
@Entity(tableName="users")
public class User{

    @ColumnInfo(name="first_name")
    public final String userFirstName;

    @ColumnInfo(name="last_name")
    public final String userLastName;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    public final String username;

    @ColumnInfo(name="password_hash")
    public final String passwdHash;

    public User(@NonNull String userFirstName, @NonNull String userLastName,
                @NonNull String username, @NonNull String passwdHash){
        this.username = username;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.passwdHash = passwdHash;
    }
}

