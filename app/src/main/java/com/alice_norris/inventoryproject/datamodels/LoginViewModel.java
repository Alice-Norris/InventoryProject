package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class LoginViewModel extends AndroidViewModel {
    private UserRepository loginUserRepository;
    private LiveData<User> currentUser = null;

    public LoginViewModel(Application application){
        super(application);
        loginUserRepository = new UserRepository(application);
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
