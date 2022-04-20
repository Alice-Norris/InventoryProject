package com.alice_norris.inventoryproject.datamodels;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

//defines the user table in the database
@Entity(tableName="users")
public class User{
    @NonNull
    @ColumnInfo(name="first_name")
    public final String userFirstName;

    @NonNull
    @ColumnInfo(name="last_name")
    public final String userLastName;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    public final String username;

    @NonNull
    @ColumnInfo(name="password")
    public final String password;

    public User(@NonNull String userFirstName, @NonNull String userLastName,
                @NonNull String username, @NonNull String password) {
        this.username = username;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.password = password;
    }
}

