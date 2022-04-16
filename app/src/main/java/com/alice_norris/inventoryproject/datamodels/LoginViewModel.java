package com.alice_norris.inventoryproject.datamodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LoginViewModel extends AndroidViewModel {

    private LiveData<User> liveUserData;
    private UserRepository loginUserRepository;
    private User currentUser;
    public LoginViewModel(Application application){
        super(application);
        loginUserRepository = new UserRepository(application);
        liveUserData = loginUserRepository.getUser();
        currentUser = liveUserData.getValue();
    }

    public LiveData<User> getUser(){
        return liveUserData;
    }

    public void login(String username, String password){
        loginUserRepository.login(username, password);
        ;
    }

    public void register(User user){
        loginUserRepository.register(user);
    }
}
