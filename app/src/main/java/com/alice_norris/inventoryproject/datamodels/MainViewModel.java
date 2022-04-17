package com.alice_norris.inventoryproject.datamodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loggedIn;
    private String userFirstName;

    public MainViewModel(){
        this.loggedIn = new MutableLiveData<>(false);
    }

    public void loginStatusChange(){
        if(!loggedIn.getValue()){
            loggedIn.setValue(true);
        } else {
            loggedIn.setValue(false);
        }
    }

    public LiveData<Boolean> getLoginStatus(){
        return this.loggedIn;
    }

    public void setUserFirstName(String userFirstName) { this.userFirstName = userFirstName; }

    public String getUserFirstName() {return this.userFirstName; }

    public void logout(){
        loggedIn.setValue(false);
    }

}
