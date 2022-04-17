package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.rxjava3.core.Single;

public class LoginViewModel extends AndroidViewModel {
    private UserRepository loginUserRepository;
    private LiveData<User> currentUser;
    public LoginViewModel(Application application){
        super(application);
        loginUserRepository = new UserRepository(application);
        currentUser = loginUserRepository.login("null", "null");
        }

    public LiveData<User> login(String username, String password){
        return loginUserRepository.login(username, password);
    }

    public void register(User user){
        loginUserRepository.register(user);
    }

    public LiveData<User> getUser(){
        return currentUser;
    }
}
