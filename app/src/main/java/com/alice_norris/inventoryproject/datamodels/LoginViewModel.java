package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class LoginViewModel extends AndroidViewModel {
    private LiveData<User> liveUserData;
    private UserRepository loginUserRepository;

    public LoginViewModel(Application application){
        super(application);
        loginUserRepository = new UserRepository(application);
        liveUserData = loginUserRepository.getUser();
    }

    public LiveData<User> getUser(){
        return liveUserData;
    }

    public void login(String username, String password){
        loginUserRepository.login(username, password);
    }

    public void register(User user){
        loginUserRepository.register(user);
    }
}
